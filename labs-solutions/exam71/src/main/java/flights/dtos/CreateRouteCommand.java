package flights.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class CreateRouteCommand {
    
    @NotBlank(message = "Departure City can not be blank")
    private String departureCity;
    @NotBlank(message = "Arrival City can not be blank")
    private String arrivalCity;
    @NotNull(message = "Date Of Flight can not be null")
    @Future
    private LocalDate dateOfFlight;
}

