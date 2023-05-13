package appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;

import appointments.controller.AppointmentsController;
import appointments.model.Appointment;
import appointments.model.AppointmentCreateCommand;
import appointments.model.AppointmentDto;
import appointments.model.CaseType;
import appointments.repository.AppointmentsRepository;

@SpringBootTest
public class ApplicationIT {
    @Autowired
    AppointmentsController controller;

    @Autowired
    AppointmentsRepository repo;

    LocalDateTime tomorrowNow = LocalDateTime.now().plusDays(1);

    @BeforeEach
	void init() {
        repo.clear();
        repo.saveAppointment(new Appointment(1234567890L, tomorrowNow.plusMinutes(30), tomorrowNow.plusMinutes(60), CaseType.C001));
        repo.saveAppointment(new Appointment(1234567891L, tomorrowNow.plusMinutes(60), tomorrowNow.plusMinutes(90), CaseType.C002));
        repo.saveAppointment(new Appointment(1234567892L, tomorrowNow.plusMinutes(90), tomorrowNow.plusMinutes(120), CaseType.C001));
	}

    @Test
    void getAppointments_getList() throws IOException{
        Assertions.assertThat(controller.listAppointments())
            .hasSize(3)
            .extracting(AppointmentDto::getPersonalTaxId)
            .containsExactlyInAnyOrder(1234567890L, 1234567891L, 1234567892L);
    }

    @Test
    void createAppointment_createdWithId() throws IOException {      
        var command = new AppointmentCreateCommand(1234567892L, tomorrowNow.plusMinutes(120), tomorrowNow.plusMinutes(150), CaseType.C002);
        var response = controller.createAppointment(command, UriComponentsBuilder.newInstance());

        Assertions.assertThat(List.of(response.getBody()))
            .extracting(AppointmentDto::getId, AppointmentDto::getPersonalTaxId)
            .contains(Assertions.tuple(4L, 1234567892L));

        assertEquals("/appointments/4", response.getHeaders().getLocation().toString());
    }

}
