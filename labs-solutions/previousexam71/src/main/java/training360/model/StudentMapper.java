package training360.model;

import java.util.List;

import org.mapstruct.Mapper;

import training360.DTOs.CreateStudentCommand;
import training360.DTOs.StudentDto;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto toDto(Student entity);
    List<StudentDto> toDto(List<Student> entities);

    Student fromDto(StudentDto entityDto);
    List<Student> fromDto(List<StudentDto> entityDtos);
    
    Student fromCreateCommand(CreateStudentCommand entityCreateCommand);

}