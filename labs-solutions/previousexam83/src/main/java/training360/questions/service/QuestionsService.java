package training360.questions.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;
import training360.questions.QuestionsException;
import training360.questions.domain.Member;
import training360.questions.domain.Question;
import training360.questions.dto.*;
import training360.questions.repository.MemberRepository;
import training360.questions.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionsService {

    private MemberRepository memberRepository;

    private QuestionRepository questionRepository;

    private ModelMapper modelMapper;

    @Transactional
    public MemberDto createMember(CreateMemberCommand command) {
        Member member = new Member(command.getName());
        memberRepository.save(member);
        return modelMapper.map(member, MemberDto.class);
    }

    @Transactional
    public MemberDto disableMember(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new QuestionsException("questions/member-not-found", "Member not found", Status.NOT_FOUND));
        member.setEnabled(false);
        return modelMapper.map(member, MemberDto.class);
    }

    @Transactional
    public QuestionDto createQuestion(long memberId, CreateQuestionCommand command) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new QuestionsException("questions/member-not-found", "Member not found", Status.NOT_FOUND));
        if (!member.isEnabled()) {
            throw new QuestionsException("questions/member-disabled", "Member is disabled", Status.BAD_REQUEST);
        }
        Question question = new Question(member, command.getQuestionText());
        questionRepository.save(question);
        return modelMapper.map(question, QuestionDto.class);
    }

    @Transactional
    public QuestionDto voteQuestion(long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionsException("questions/question-not-found", "Question not found", Status.NOT_FOUND));
        question.setVotes(question.getVotes() + 1);
        return modelMapper.map(question, QuestionDto.class);
    }

    @Transactional
    public QuestionDto answer(long questionId, AnswerCommand command) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionsException("questions/question-not-found", "Question not found", Status.NOT_FOUND));
        question.setAnswered(true);
        question.setAnswerText(command.getAnswerText());
        question.setAnsweredAt(LocalDateTime.now());
        return modelMapper.map(question, QuestionDto.class);
    }

    public List<NameQuestionPair> getPairs() {
        return questionRepository.findPairs();
    }

    public List<MemberDto> getMembers() {
        java.lang.reflect.Type targetListType = new TypeToken<List<MemberDto>>() {}.getType();
        return modelMapper.map(memberRepository.findAll(), targetListType);
    }

    public List<QuestionDto> getQuestions(long memberId) {
        java.lang.reflect.Type targetListType = new TypeToken<List<QuestionDto>>() {}.getType();
        return modelMapper.map(memberRepository.findById(memberId).orElseThrow(() -> new QuestionsException("questions/member-not-found", "Member not found", Status.NOT_FOUND)).getQuestions(), targetListType);
    }
}
