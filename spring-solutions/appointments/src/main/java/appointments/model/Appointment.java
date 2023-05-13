package appointments.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private Long id;
    private long personalTaxId;
    private LocalDateTime start;
    private LocalDateTime end;
    private CaseType caseType;

    public Appointment(long personalTaxId, LocalDateTime start, LocalDateTime end, CaseType caseType) {
        this(null, personalTaxId, start, end, caseType);
    }

}
