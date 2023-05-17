package training360.sv2023jvjbfkepesitovizsga.dtos;

import java.util.List;

import org.mapstruct.Mapper;

import training360.sv2023jvjbfkepesitovizsga.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto toDto(Student entity);
    List<StudentDto> toDto(List<Student> entities);

    Student fromDto(StudentDto entityDto);
    List<Student> fromDto(List<StudentDto> entityDtos);

}
