package appointments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appointments.model.Appointment;
import appointments.model.CaseType;
import appointments.repository.AppointmentsRepository;

public class AppointmentsRepositoryTest {
    AppointmentsRepository repo = new AppointmentsRepository();

    LocalDateTime tomorrowNow = LocalDateTime.now().plusDays(1);

    @BeforeEach
    void initAppointments() {
        repo.clear();
        repo.saveAppointment(new Appointment(1234567890L, tomorrowNow.plusMinutes(30), tomorrowNow.plusMinutes(60), CaseType.C001));
        repo.saveAppointment(new Appointment(1234567891L, tomorrowNow.plusMinutes(60), tomorrowNow.plusMinutes(90), CaseType.C002));
    }

    @Test
    void readAppointmentById() {
        assertEquals(1234567891, repo.getAppointmentById(2L).getPersonalTaxId());
    }
    
    @Test
    void readAllAppointments() {
        assertThat(repo.getAppointments())
            .hasSize(2)
            .extracting(Appointment::getPersonalTaxId)
            .contains(1234567890L, 1234567891L)
            .doesNotContain(9876543210L);
    }

    @Test
    void saveAppointment() {
        repo.saveAppointment(new Appointment(1234567892L, tomorrowNow.plusMinutes(90), tomorrowNow.plusMinutes(120), CaseType.C001));
        assertThat(repo.getAppointments())
            .hasSize(3)
            .extracting(Appointment::getPersonalTaxId)
            .containsOnly(1234567890L,1234567891L,1234567892L);
    }

}
