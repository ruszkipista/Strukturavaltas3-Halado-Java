package locations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.nio.file.Path;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
public class LocationsReaderAssertJTest {
    List<Location> locations;
    Path path = Path.of("src/test/resources/locations.csv");

    @BeforeEach
    void initLocations() {
        LocationsReader reader = new LocationsReader();
        locations = reader.readFromCsvFile(path, new LocationParser());
    }

    @Test
    void testLocationReadFromCsvFile() {
        assertThat(locations)
                .hasSize(5)
                .extracting(Location::getName)
                .contains("Budapest", "Debrecen")
                .doesNotContain("Dublin");
    }

    @Test
    void testOverArcticLocations() {
        List<Location> arcticLocations = LocationsReader.filterLocationsBeyondArcticCircle(locations);
        assertThat(arcticLocations)
                .extracting(Location::getName)
                .hasSize(2)
                .contains("Dermeszto")
                .doesNotContain("Debrecen")
                .containsOnly("Dermeszto", "NagyonHideg");

        assertThat(arcticLocations)
                .filteredOn(l -> l.getLatitude() == l.getLongitude())
                .extracting(Location::getName, Location::getLatitude)
                .containsOnly(tuple("Dermeszto", 70.0));
    }

    @Disabled
    @Test
    void trySoftAssert(SoftAssertions softly){
        Location location = new Location("Abc", 0, 0);

        softly.assertThat(location.getName())
            .startsWith("b")
            .endsWith("b");

    }
}
