package training360.sv2023jvjbfkepesitovizsga.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentCommand {

    @NotBlank(message = "schoolName can not be blank")
    private String schoolName;

    @NotBlank(message = "city can not be blank")
    private String city;
}