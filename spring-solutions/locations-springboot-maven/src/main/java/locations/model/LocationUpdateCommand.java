package locations.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationUpdateCommand {
    @NotBlank(message = "name can not be blank")
    private String name;
    // @Min(-90)
    // @Max(+90)
    @Coordinate
    private double latitude;
    // @Min(-180)
    // @Max(+180)
    @Coordinate(type = CoordinateType.LON)
    private double longitude;
}