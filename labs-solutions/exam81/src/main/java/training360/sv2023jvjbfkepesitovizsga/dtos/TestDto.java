package training360.sv2023jvjbfkepesitovizsga.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.sv2023jvjbfkepesitovizsga.model.TestValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {
    private Long id;
    private String subject;
    private TestValue testValue;
    private StudentWithoutTestsDto student;
}
