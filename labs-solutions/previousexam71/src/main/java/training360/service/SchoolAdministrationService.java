package training360.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import training360.DTOs.CreateSchoolCommand;
import training360.DTOs.CreateStudentCommand;
import training360.DTOs.SchoolDto;
import training360.exceptions.SchoolNotFoundException;
import training360.exceptions.StudentNotFoundException;
import training360.model.Address;
import training360.model.School;
import training360.model.SchoolAgeStatus;
import training360.model.SchoolMapper;
import training360.model.Student;
import training360.repository.SchoolRepository;
import training360.repository.StudentRepository;

@Service
@AllArgsConstructor
public class SchoolAdministrationService {

    private SchoolMapper mapper;
    private SchoolRepository schoolRepository;

    private StudentRepository studentRepository;

    public SchoolDto createSchool(CreateSchoolCommand createSchoolCommand) {
        Address address = new Address(
            createSchoolCommand.getPostalCode(), 
            createSchoolCommand.getCity(),
            createSchoolCommand.getStreet(),
            createSchoolCommand.getHouseNumber());
        School school = new School(
            createSchoolCommand.getName(), 
            address);
        schoolRepository.save(school);
        return mapper.toDto(school);
    }

    @Transactional
    public SchoolDto createStudent(long id, CreateStudentCommand createStudentCommand) {
        School school = schoolRepository.findById(id)
                                        .orElseThrow(() -> new SchoolNotFoundException(id));
        Student student = new Student(
            createStudentCommand.getName(), 
            createStudentCommand.getDateOfBirth(),
            SchoolAgeStatus.getStatus(ChronoUnit.YEARS.between(createStudentCommand.getDateOfBirth(), LocalDate.now() )) );
        studentRepository.save(student);
        school.addStudent(student);
        return mapper.toDto(school);
    }

    public List<SchoolDto> getSchools(Optional<String> city) {
        return mapper.toDto(schoolRepository.findByParams(city));
    }

    public SchoolDto fireFromSchool(long schoolId, long studentId) {
        School school = schoolRepository.findById(schoolId)
                                        .orElseThrow(() -> new SchoolNotFoundException(schoolId));
        Student student = studentRepository.findById(studentId)
                                           .orElseThrow(() -> new StudentNotFoundException(studentId));
        if (student.getSchool().equals(school)) {
            student.setSchool(null);
            schoolRepository.save(school);
        } else {
            throw new StudentNotFoundException(studentId);
        }
        School schoolUpdated = schoolRepository.findById(schoolId)
                                                .orElseThrow(() -> new SchoolNotFoundException(schoolId));
        return mapper.toDto(schoolUpdated);
    }

}
