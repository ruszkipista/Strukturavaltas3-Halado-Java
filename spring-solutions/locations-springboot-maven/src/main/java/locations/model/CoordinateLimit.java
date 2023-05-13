package locations.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoordinateLimit {
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;

}
