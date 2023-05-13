package appointments.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import appointments.model.Appointment;
import appointments.service.AppointmentNotFoundException;

@Repository
public class AppointmentsRepository {
    private List<Appointment> appointments ;
    private AtomicLong lastId;
    
    public AppointmentsRepository() {
        clear();
    }

    public List<Appointment> getAppointments() {
        return this.appointments;
    }

    public Appointment getAppointmentById(long appointmentId) {
        return this.appointments.stream()
                .filter(l -> l.getId() == appointmentId)
                .findAny()
                .orElseThrow(()-> new AppointmentNotFoundException("Unable to find appointment " + appointmentId));
    }

    public Appointment saveAppointment(Appointment appointment) {
        appointment.setId(this.lastId.incrementAndGet());
        this.appointments.add(appointment);
        return appointment;
    }

    public void clear() {
        this.appointments = Collections.synchronizedList(new ArrayList<>());
        this.lastId = new AtomicLong();
    }

}
