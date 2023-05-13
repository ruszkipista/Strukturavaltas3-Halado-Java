package appointments;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import appointments.model.Appointment;
import appointments.model.AppointmentDto;
import appointments.model.AppointmentMapperImpl;
import appointments.model.CaseType;
import appointments.repository.AppointmentsRepository;
import appointments.service.AppointmentsService;

@ExtendWith(MockitoExtension.class)
public class AppointmentsServiceTest {
    @Mock
    AppointmentsRepository repo;

    @InjectMocks
    AppointmentsService service = new AppointmentsService(repo, new AppointmentMapperImpl());

    List<Appointment> appointments;
    LocalDateTime tomorrowNow = LocalDateTime.now().plusDays(1);


    @BeforeEach
    void init() {
        appointments = List.of(
            new Appointment(1234567890, tomorrowNow.plusMinutes(30), tomorrowNow.plusMinutes(60), CaseType.C001),
            new Appointment(1234567891, tomorrowNow.plusMinutes(60), tomorrowNow.plusMinutes(90), CaseType.C002),
            new Appointment(1234567892, tomorrowNow.plusMinutes(90), tomorrowNow.plusMinutes(120), CaseType.C001)
        );
    }

    @Test
    void getAppointments_returnsList() throws IOException {
        Mockito.when(repo.getAppointments()).thenReturn(appointments);

        Assertions.assertThat(service.getAppointments())
            .hasSize(3)
            .extracting(AppointmentDto::getPersonalTaxId, AppointmentDto::getCaseType)
            .containsExactlyInAnyOrder(
                Assertions.tuple(1234567890L, CaseType.C001),
                Assertions.tuple(1234567891L, CaseType.C002),
                Assertions.tuple(1234567892L, CaseType.C001)
            );

        verify(repo, times(1)).getAppointments();
    }
    
}
