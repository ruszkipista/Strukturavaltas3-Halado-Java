package training360.questions.dto;

import lombok.Data;

@Data
public class QuestionDto {

    private Long id;

    private boolean answered;

    private String question;

    private String answer;

    private int votes;

}
