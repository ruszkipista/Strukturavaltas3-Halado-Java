package appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import appointments.controller.AppointmentsController;
import appointments.model.AppointmentCreateCommand;
import appointments.model.AppointmentDto;
import appointments.model.CaseType;
import appointments.service.AppointmentNotFoundException;
import appointments.service.AppointmentsService;

@ExtendWith(MockitoExtension.class)
public class AppointmentsControllerTest {

    @Mock
    AppointmentsService service;

    @InjectMocks
    AppointmentsController controller;

    @Mock
    UriComponentsBuilder builder;
    @Mock
    UriComponents components;

    List<AppointmentDto> appointments;
    LocalDateTime tomorrowNow = LocalDateTime.now().plusDays(1);

    @BeforeEach
    void intit(){
        appointments = List.of(
            new AppointmentDto(1L, 1234567890L, tomorrowNow.plusMinutes(30), tomorrowNow.plusMinutes(60), CaseType.C001),
            new AppointmentDto(2L, 1234567891L, tomorrowNow.plusMinutes(60), tomorrowNow.plusMinutes(90), CaseType.C002),
            new AppointmentDto(3L, 1234567892L, tomorrowNow.plusMinutes(90), tomorrowNow.plusMinutes(120), CaseType.C001)
        );
    }
 
    @Test
    void listAllAppointments_listed() throws IOException{
        Mockito.when(service.getAppointments()).thenReturn(appointments);

        Assertions.assertEquals(appointments, controller.listAppointments());

        Mockito.verify(service).getAppointments();
    }

    @Test
    public void testCreateAppointment() {
        // create an AppointmentCreateCommand instance
        AppointmentCreateCommand command = new AppointmentCreateCommand(1234567892L, tomorrowNow.plusMinutes(90), tomorrowNow.plusMinutes(120), CaseType.C001);
        // create an AppointmentDto instance
        AppointmentDto appointmentDto = new AppointmentDto(42L, 1234567892L, tomorrowNow.plusMinutes(90), tomorrowNow.plusMinutes(120), CaseType.C001);

        // mock the service method to return the AppointmentDto instance
        when(service.createAppointment(command)).thenReturn(appointmentDto);

        URI expectedAppointment = URI.create("http://localhost/appointments/42");

        when(builder.path(any())).thenReturn(builder);
        when(builder.buildAndExpand(anyLong())).thenReturn(components);
        when(components.toUri()).thenReturn(expectedAppointment);
              
        // call the createAppointment method on the controller
        var response = controller.createAppointment(command, builder);
        
        // verify that the service method was called with the command argument
        verify(service).createAppointment(command);
        
        // verify that the response status is CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        
        // verify that the AppointmentDto instance is returned in the response body
        assertEquals(appointmentDto, response.getBody());
        
        // verify that the response header contains a Location header with the URI of the new appointment
        assertEquals(expectedAppointment, response.getHeaders().getLocation());
    }

    @Test
    public void getById_appointmentNotFound_404() {
        doThrow(new AppointmentNotFoundException("NotFound")).when(service).getAppointmentById(anyLong()); 

        assertEquals(HttpStatus.NOT_FOUND, controller.getAppointmentsById(42L).getStatusCode());
    }

}
