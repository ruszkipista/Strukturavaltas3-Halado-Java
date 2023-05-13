package locations;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationsRepositoryTest {
    List<Location> locations;

    @BeforeEach
    void initLocations() {
        Path path = Path.of("src/test/resources/locations.csv");
        LocationsRepository repo = new LocationsRepository(path);
        locations = repo.getFavouriteLocations();
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
