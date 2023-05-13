package training360.questions;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import training360.questions.dto.*;
import training360.questions.service.QuestionsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class QuestionsController {

    private QuestionsService questionsService;

    @GetMapping("/members")
    public List<MemberDto> getMembers() {
        return questionsService.getMembers();
    }

    @GetMapping("/members/{memberId}/questions")
    public List<QuestionDto> getQuestions(@PathVariable("memberId") long memberId) {
        return questionsService.getQuestions(memberId);
    }

    @PostMapping("/members")
    public MemberDto createMember(@Valid @RequestBody CreateMemberCommand command) {
        return questionsService.createMember(command);
    }

    @PostMapping("/members/{id}/enabled")
    public MemberDto disableMember(@PathVariable("id") long memberId) {
        return questionsService.disableMember(memberId);
    }

    @PostMapping("/members/{id}/questions")
    public QuestionDto createQuestion(@PathVariable("id") long memberId, @Valid @RequestBody CreateQuestionCommand command) {
        return questionsService.createQuestion(memberId, command);
    }

    @PostMapping("/members/{memberId}/questions/{questionId}/votes")
    public QuestionDto voteQuestion(@PathVariable("memberId") long memberId, @PathVariable("questionId") long questionId) {
        return questionsService.voteQuestion(questionId);
    }

    @PutMapping("/members/{memberId}/questions/{questionId}/answer")
    public QuestionDto answer(@PathVariable("memberId") long memberId, @PathVariable("questionId") long questionId, @Valid @RequestBody AnswerCommand command) {
        return questionsService.answer(questionId, command);
    }

    @GetMapping("/pairs")
    public List<NameQuestionPair> getPairs() {
        return questionsService.getPairs();
    }
}
