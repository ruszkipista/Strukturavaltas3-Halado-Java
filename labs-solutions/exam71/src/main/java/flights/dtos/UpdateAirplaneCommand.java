package flights.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAirplaneCommand {
    
    @NotBlank(message = "Owner airline can not be blank")
    private String ownerAirline;
}