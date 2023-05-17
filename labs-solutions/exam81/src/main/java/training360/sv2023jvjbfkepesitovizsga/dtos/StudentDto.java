package training360.sv2023jvjbfkepesitovizsga.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private SchoolDto school;
    private List<TestWithoutStudentDto> tests;
}
