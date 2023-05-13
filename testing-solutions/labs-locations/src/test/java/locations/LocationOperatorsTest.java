package locations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class LocationOperatorsTest {

    @LocationOperationsFeatureTest
    void testFilterOnNorth_onlyNorthHemisphere(){
        List<Location> locations = List.of(
            new Location("N1", 1, 1),
            new Location("N2", 1, -1),
            new Location("E1", 0, 1),
            new Location("E2", 0, -1),
            new Location("S1", -1, 1),
            new Location("S2", -1, -1)
        );

        List<Location> locationsFiltered = new LocationOperators().filterOnNorth(locations);

        assertEquals(List.of("N1", "N2"), 
                    locationsFiltered.stream().map(Location::getName).toList());
    }
}
