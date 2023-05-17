package training360.sv2023jvjbfkepesitovizsga.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class CreateTestCommand {
    
    @NotBlank(message = "subject can not be blank")
    private String subject;

    @Positive
    private int maxPoints;

    @Positive
    private int points;
}

