package training360.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import training360.DTOs.CreateSchoolCommand;
import training360.DTOs.CreateStudentCommand;
import training360.DTOs.SchoolDto;
import training360.service.SchoolAdministrationService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private SchoolAdministrationService schoolAdministrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolDto createSchool(@Valid @RequestBody CreateSchoolCommand createSchoolCommand) {
        return schoolAdministrationService.createSchool(createSchoolCommand);
    }

    @PostMapping("/{id}/students")
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolDto createStudent(@PathVariable("id") long id,
            @Valid @RequestBody CreateStudentCommand createStudentCommand) {
        return schoolAdministrationService.createStudent(id, createStudentCommand);
    }

    @GetMapping
    public List<SchoolDto> getSchools(@RequestParam Optional<String> city) {
        return schoolAdministrationService.getSchools(city);
    }

    @PutMapping("/{id}/students/{stdId}")
    public SchoolDto fireFromSchool(@PathVariable("id") long schoolId, @PathVariable("stdId") long studentId) {
        return schoolAdministrationService.fireFromSchool(schoolId, studentId);
    }

}
