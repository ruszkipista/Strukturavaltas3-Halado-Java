package training360.DTOs;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.model.Address;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchoolDto {

    private Long id;
    private String name;
    private Address address;
    private List<StudentDto> students = new ArrayList<>();
}
