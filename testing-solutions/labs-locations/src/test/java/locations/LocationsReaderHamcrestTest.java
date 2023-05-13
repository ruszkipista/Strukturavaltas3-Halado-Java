package locations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWithIgnoringCase;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LocationsReaderHamcrestTest {
    List<Location> locations;
    Path path = Path.of("src/test/resources/locations.csv");

    @Test
    void testLocationReadFromCsvFile(){
        LocationsReader reader = new LocationsReader();
        locations = reader.readFromCsvFile(path, new LocationParser());

        assertThat(locations.size(), equalTo(5));
        Location location = locations.get(1);

        assertThat(location, instanceOf(Location.class));

        assertThat(location.getName(), is(equalTo("Debrecen")));
        assertThat(location.getName(), endsWith("en"));

        assertThat(location.getLatitude(), closeTo(47.53, 0.0001));
        assertThat(location.getLongitude(), greaterThan(21.0));

        assertThat(location, hasProperty("name", equalTo("Debrecen")));

        assertThat(location, allOf(hasProperty("name", equalTo("Debrecen")),
                                    hasProperty("longitude", greaterThan(21.0))));

        assertThat(locations, hasItem(hasProperty("name", startsWithIgnoringCase("bud"))));
    }
}
