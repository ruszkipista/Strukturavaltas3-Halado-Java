package training360.DTOs;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.model.School;
import training360.model.SchoolAgeStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentCommand {

    public CreateStudentCommand(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    @NotBlank(message="Student name cannot be blank!")
    private String name;

    @Past(message = "Student date of birth must be in past!")
    private LocalDate dateOfBirth;
    private SchoolAgeStatus schoolAgeStatus;
    private School school;

}
