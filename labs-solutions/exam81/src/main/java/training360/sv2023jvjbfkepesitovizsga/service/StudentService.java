package training360.sv2023jvjbfkepesitovizsga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import training360.sv2023jvjbfkepesitovizsga.dtos.CreateStudentCommand;
import training360.sv2023jvjbfkepesitovizsga.dtos.CreateTestCommand;
import training360.sv2023jvjbfkepesitovizsga.dtos.StudentDto;
import training360.sv2023jvjbfkepesitovizsga.dtos.StudentMapper;
import training360.sv2023jvjbfkepesitovizsga.dtos.UpdateStudentCommand;
import training360.sv2023jvjbfkepesitovizsga.exception.StudentNotFoundException;
import training360.sv2023jvjbfkepesitovizsga.model.Student;
import training360.sv2023jvjbfkepesitovizsga.model.Test;
import training360.sv2023jvjbfkepesitovizsga.model.TestValue;
import training360.sv2023jvjbfkepesitovizsga.repository.StudentRepository;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentRepository repoStudent;
    private StudentMapper mapper;

    @Transactional
    public List<StudentDto> getStudents() {
        return this.mapper.toDto(repoStudent.findAll());
    }

    public StudentDto getStudentById(long id) {
        return this.mapper.toDto(repoStudent.findById(id)
                                     .orElseThrow(()->new StudentNotFoundException(id)));
    }
    
    public StudentDto createStudent(CreateStudentCommand command) {
        Student studentToCreate = new Student(
            command.getName(),
            command.getSchoolName(),
            command.getCity());
        Student entity = repoStudent.save(studentToCreate);
        return this.mapper.toDto(entity);
    }

    @Transactional
    public StudentDto updateStudentById(long id, UpdateStudentCommand command) {
        Student entity = repoStudent.findById(id)
                         .orElseThrow(()->new StudentNotFoundException(id));
        entity.getSchool().setSchoolName(command.getSchoolName());
        entity.getSchool().setCity(command.getCity());
        return this.mapper.toDto(entity);
    }

    @Transactional
    public StudentDto createTest(long id, @Valid CreateTestCommand command) {
        Student student = repoStudent.findById(id)
                                     .orElseThrow(()->new StudentNotFoundException(id));
        student.addTest(new Test(
            command.getSubject(),
            getTestValue(command.getMaxPoints(), command.getPoints()),
            student
        ));
        return this.mapper.toDto(student);
    }

    private TestValue getTestValue(int max, int actual) {
        double percentage = max/actual*100.0;
        if (percentage >= TestValue.PERFECT.getPercentage())
            return TestValue.PERFECT;
        else if (percentage > TestValue.AVERAGE.getPercentage())
            return TestValue.AVERAGE;
        return TestValue.NOT_PASSED;
    }

    @Transactional
    public List<StudentDto> getStudentByIdWithTests(long id, Optional<String> subject) {
        Student student = repoStudent.findById(id)
                                     .orElseThrow(()->new StudentNotFoundException(id));
        List<Student> students = repoStudent.getStudentByIdWithTests(id, subject);
        return this.mapper.toDto(students);
    }

}
