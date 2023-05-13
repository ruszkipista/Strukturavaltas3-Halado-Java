package appointments.service;

import java.util.List;

import org.springframework.stereotype.Service;

import appointments.model.Appointment;
import appointments.model.AppointmentCreateCommand;
import appointments.model.AppointmentDto;
import appointments.model.AppointmentMapper;
import appointments.repository.AppointmentsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentsService {
    private AppointmentsRepository repo;
    private AppointmentMapper appointmentMapper;

    public List<AppointmentDto> getAppointments() {
        return this.appointmentMapper.toDto(repo.getAppointments());
    }

    public AppointmentDto getAppointmentById(long appointmentId) {
        return this.appointmentMapper.toDto(repo.getAppointmentById(appointmentId));
    }

    public AppointmentDto createAppointment(AppointmentCreateCommand command) {
        Appointment appointment = repo.saveAppointment(this.appointmentMapper.fromCreateCommand(command));
        return this.appointmentMapper.toDto(appointment);
    }

}
