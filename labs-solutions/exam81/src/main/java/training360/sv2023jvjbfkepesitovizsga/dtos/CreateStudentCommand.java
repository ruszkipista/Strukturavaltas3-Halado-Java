package training360.sv2023jvjbfkepesitovizsga.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.sv2023jvjbfkepesitovizsga.model.School;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class CreateStudentCommand {
    
    public CreateStudentCommand(String name, School school) {
        this.name = name;
        this.schoolName = school.getSchoolName();
        this.city = school.getCity();
    }

    @NotBlank(message = "name can not be blank")
    private String name;

    @NotBlank(message = "schoolName can not be blank")
    private String schoolName;

    @NotBlank(message = "city can not be blank")
    private String city;

}

