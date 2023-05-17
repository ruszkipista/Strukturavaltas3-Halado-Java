package training360.sv2023jvjbfkepesitovizsga.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import training360.sv2023jvjbfkepesitovizsga.dtos.CreateStudentCommand;
import training360.sv2023jvjbfkepesitovizsga.dtos.CreateTestCommand;
import training360.sv2023jvjbfkepesitovizsga.dtos.StudentDto;
import training360.sv2023jvjbfkepesitovizsga.dtos.UpdateStudentCommand;
import training360.sv2023jvjbfkepesitovizsga.service.StudentService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private StudentService service;

    @GetMapping
    public List<StudentDto> listStudents() {
        return service.getStudents();
    }

    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StudentDto> getStudentById(
            @PathVariable("id") 
            long id) {
        return ResponseEntity.ok(service.getStudentById(id));
    }

    @GetMapping("{id}/tests")
    public ResponseEntity<List<StudentDto>> createTest(
            @PathVariable("id") 
            long id,
            @RequestParam 
            Optional<String> subject) {
        List<StudentDto> entityDtos = service.getStudentByIdWithTests(id, subject);
        return ResponseEntity.ok(entityDtos);

    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(
            @Valid 
            @RequestBody 
            CreateStudentCommand command,
            UriComponentsBuilder uriComponentsBuilder ) {
        StudentDto entityDto = service.createStudent(command);
        URI locationUri = uriComponentsBuilder
            .path("/api/students/{id}")
            .buildAndExpand(entityDto.getId())
            .toUri();
        return ResponseEntity
            .created(locationUri)
            .body(entityDto);
    }

    @PostMapping("{id}/tests")
    public ResponseEntity<StudentDto> createTest(
            @PathVariable("id") 
            long id,
            @Valid 
            @RequestBody 
            CreateTestCommand command,
            UriComponentsBuilder uriComponentsBuilder ) {
        StudentDto entityDto = service.createTest(id, command);
        URI locationUri = uriComponentsBuilder
            .path("/api/tests/{id}")
            .buildAndExpand(entityDto.getId())
            .toUri();
        return ResponseEntity
            .created(locationUri)
            .body(entityDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<StudentDto> modifyStudentById(
            @PathVariable("id") 
            long id,
            @Valid 
            @RequestBody 
            UpdateStudentCommand command) {
        StudentDto entityDto = service.updateStudentById(id, command);
        return ResponseEntity.ok(entityDto);
    }

}