package appointments.model;

import java.time.LocalDateTime;

import appointments.service.ValidCDV;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreateCommand {
    @ValidCDV
    private long personalTaxId;
    @Future
    private LocalDateTime start;
    @Future
    private LocalDateTime end;
    private CaseType caseType;
}
