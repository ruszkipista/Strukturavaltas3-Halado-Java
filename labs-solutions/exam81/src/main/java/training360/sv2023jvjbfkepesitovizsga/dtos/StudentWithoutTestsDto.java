package training360.sv2023jvjbfkepesitovizsga.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithoutTestsDto {
    private Long id;
    private String name;
    private String schoolName;
    private String city;
}
