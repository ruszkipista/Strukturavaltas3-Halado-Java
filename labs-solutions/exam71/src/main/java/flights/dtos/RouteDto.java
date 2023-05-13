package flights.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {
    private Long id;
    // private Airplane airplane;
    private String departureCity;
    private String arrivalCity;
    private LocalDate dateOfFlight;
}
