package training360.questions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.jdbc.Sql;
import training360.questions.dto.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from questions", "delete from members"})
class QuestionsIT {

    @Autowired
    TestRestTemplate template;

    @Test
    void testCreateAndListMember() {
        CreateMemberCommand command = new CreateMemberCommand("John Doe");
        MemberDto member = template.postForObject("/api/members", command, MemberDto.class);

        assertEquals("John Doe", member.getName());
        
        List<MemberDto> members = template.exchange("/api/members",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MemberDto>>(){}).getBody();
        assertThat(members)
                .extracting(MemberDto::getName)
                .containsExactly("John Doe");
    }

    @Test
    void testCreateAndListQuestion() {
        CreateMemberCommand createMemberCommand = new CreateMemberCommand("John Doe");
        MemberDto member = template.postForObject("/api/members", createMemberCommand, MemberDto.class);

        CreateQuestionCommand command = new CreateQuestionCommand("Why is Spring Boot so popular?");
        QuestionDto question = template.postForObject("/api/members/{id}/questions", command, QuestionDto.class, member.getId());

        assertThat(question)
                .extracting(QuestionDto::getQuestion, QuestionDto::isAnswered, QuestionDto::getAnswer)
                .containsSequence("Why is Spring Boot so popular?", false, null);

        List<QuestionDto> questions = template.exchange("/api/members/{id}/questions",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<QuestionDto>>(){}, member.getId()).getBody();
        assertThat(questions)
                .extracting(QuestionDto::getQuestion)
                .containsExactly("Why is Spring Boot so popular?");
    }

    @Test
    void testCreateAndAnswerQuestion() {
        CreateMemberCommand createMemberCommand = new CreateMemberCommand("John Doe");
        MemberDto member = template.postForObject("/api/members", createMemberCommand, MemberDto.class);

        CreateQuestionCommand command = new CreateQuestionCommand("Why is Spring Boot so popular?");
        QuestionDto question = template.postForObject("/api/members/{id}/questions", command, QuestionDto.class, member.getId());

        QuestionDto answered = template.exchange("/api/members/{memberId}/questions/{questionId}/answer",
                HttpMethod.PUT,new HttpEntity(
                    new AnswerCommand("Because it is a good framework for creating Java microservices.")),
                QuestionDto.class, member.getId(), question.getId()).getBody();

        assertThat(answered)
                .extracting(QuestionDto::getQuestion, QuestionDto::isAnswered, QuestionDto::getAnswer)
                .containsSequence("Why is Spring Boot so popular?", true, "Because it is a good framework for creating Java microservices.");
    }

    @Test
    void testGetPairs() {
        CreateMemberCommand createMemberCommand = new CreateMemberCommand("John Doe");
        MemberDto member = template.postForObject("/api/members", createMemberCommand, MemberDto.class);

        CreateQuestionCommand command;

        command = new CreateQuestionCommand("q2");
        template.postForObject("/api/members/{id}/questions", command, QuestionDto.class, member.getId());

        command = new CreateQuestionCommand("q1");
        template.postForObject("/api/members/{id}/questions", command, QuestionDto.class, member.getId());

        createMemberCommand = new CreateMemberCommand("Jack Doe");
        MemberDto member2 = template.postForObject("/api/members", createMemberCommand, MemberDto.class);

        command = new CreateQuestionCommand("q3");
        template.postForObject("/api/members/{id}/questions", command, QuestionDto.class, member2.getId());

        command = new CreateQuestionCommand("q4");
        QuestionDto question = template.postForObject("/api/members/{id}/questions", command, QuestionDto.class, member2.getId());

        template.exchange("/api/members/{memberId}/questions/{questionId}/answer",
                HttpMethod.PUT,new HttpEntity(
                        new AnswerCommand("a4")),
                QuestionDto.class, member2.getId(), question.getId()).getBody();

        List<NameQuestionPair> questions = template.exchange("/api/pairs",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<NameQuestionPair>>(){}).getBody();
        assertThat(questions)
                .extracting(NameQuestionPair::getName, NameQuestionPair::getQuestion)
                .containsExactly(
                        tuple("Jack Doe", "q3"),
                        tuple("John Doe", "q1"),
                        tuple("John Doe", "q2")
                );
    }
}
