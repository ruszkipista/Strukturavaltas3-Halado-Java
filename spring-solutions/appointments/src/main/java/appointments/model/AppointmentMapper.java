package appointments.model;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDto toDto(Appointment appointment);
    List<AppointmentDto> toDto(List<Appointment> appointments);

    Appointment fromDto(AppointmentDto appointmentDto);
    List<Appointment> fromDto(List<AppointmentDto> appointmentDtos);
    
    AppointmentCreateCommand toCreateCommand(Appointment appointment);
    Appointment fromCreateCommand(AppointmentCreateCommand appointmentCreateCommand);

}
