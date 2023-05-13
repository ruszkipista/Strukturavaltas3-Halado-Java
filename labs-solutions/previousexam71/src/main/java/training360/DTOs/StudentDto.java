package training360.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.model.SchoolAgeStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDto {

    private String name;
    private Long id;
    private LocalDate dateOfBirth;
    private SchoolAgeStatus schoolAgeStatus;

}
