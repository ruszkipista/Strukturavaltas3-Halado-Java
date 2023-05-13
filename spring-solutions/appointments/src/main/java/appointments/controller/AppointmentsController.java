package appointments.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import appointments.model.AppointmentCreateCommand;
import appointments.model.AppointmentDto;
import appointments.service.AppointmentNotFoundException;
import appointments.service.AppointmentsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@AllArgsConstructor
public class AppointmentsController {
    private AppointmentsService service;

    // accept optional URL parameter ...?name=Abc
    @GetMapping("/appointments")
    public List<AppointmentDto> listAppointments() {
        return service.getAppointments();
    }

    // expect path variable .../42
    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentsById(@PathVariable("id") long appointmentId) {
        try {
            return ResponseEntity.ok(service.getAppointmentById(appointmentId));
        } catch (AppointmentNotFoundException iae) {
            return ResponseEntity.notFound().build();
        }

    }

    // POST appointment attributes in a JSON structure sent in the body of the request
    @PostMapping(value="/appointments")
    public ResponseEntity<AppointmentDto> createAppointment(
            @Valid @RequestBody AppointmentCreateCommand command,
            UriComponentsBuilder uriComponentsBuilder ) {
        AppointmentDto appointmentDto = service.createAppointment(command);
        URI appointmentUri = uriComponentsBuilder
            .path("/appointments/{id}")
            .buildAndExpand(appointmentDto.getId())
            .toUri();
        return ResponseEntity
            .created(appointmentUri)
            .body(appointmentDto);
    }


    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail resourceNotFoundNew(Exception e){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setType(URI.create("appointments/appointment-not-found"));
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail requestNotValid(MethodArgumentNotValidException e){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        problem.setType(URI.create("appointments/request-not-valid"));
        problem.setProperty("violations", e.getBindingResult().getFieldErrors().stream().map((FieldError fe) -> new Violation(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList()));
        return problem;
    }

    @Data
    @AllArgsConstructor
    private class Violation{
        String field;
        String message;
    }
}