package training360.questions.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean answered;

    private String questionText;

    private String answerText;

    private int votes;

    private LocalDateTime createdAt;

    private LocalDateTime answeredAt;

    @ManyToOne
    private Member member;

    public Question(Member member, String questionText) {
        this.questionText = questionText;
        this.member = member;
        createdAt = LocalDateTime.now();
        member.getQuestions().add(this);
    }
}
