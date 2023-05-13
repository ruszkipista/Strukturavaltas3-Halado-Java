package locations;

import java.util.List;

public class LocationOperators {

    public List<Location> filterOnNorth(List<Location> locations) {
        return locations.stream()
                .filter(l -> l.getLatitude()>0)
                .toList();
    }
}
