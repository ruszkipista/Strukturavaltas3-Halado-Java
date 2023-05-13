package flights.dtos;

import flights.model.AirplaneType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class CreateAirplaneCommand {
    
    private AirplaneType airplaneType;
    
    @NotBlank(message = "Owner airline can not be blank")
    private String ownerAirline;

}

