package flights.dtos;

import java.util.Set;

import flights.model.AirplaneType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneDto {
    private Long id;
    private String ownerAirline;
    private AirplaneType airplaneType;
    private Set<RouteDto> routes;
}
