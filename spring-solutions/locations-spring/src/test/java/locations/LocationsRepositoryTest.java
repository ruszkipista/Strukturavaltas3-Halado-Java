package locations;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocationsRepositoryTest {
    List<Location> locations;
    Path path = Path.of("src/test/resources/locations.csv");

    @BeforeEach
    void initLocations() {
        locations = LocationsRepository.readFromCsvFile(path);
    }

    @Test
    void testLocationReadFromCsvFile() {
        assertThat(locations)
                .hasSize(5)
                .extracting(Location::getName)
                .contains("Budapest", "Debrecen")
                .doesNotContain("Dublin");
    }

}
