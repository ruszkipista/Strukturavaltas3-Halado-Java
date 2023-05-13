package locations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocationParserTest {
    String locationString = "Budapest,47.497912,19.040235";
    LocationParser parser;

    @BeforeEach
    void initParser(){
        parser = new LocationParser();
    }
    
    @Test
    void testParse_eachAttributeOk(){
        Location location = parser.parse(locationString);

        assertEquals("Budapest", location.getName());
        assertEquals(47.497912, location.getLatitude());
        assertEquals(19.040235, location.getLongitude());
    }

    @Test
    void doubleParse_resultsTwoDifferentInstances(){
        Location location1 = parser.parse(locationString);
        Location location2 = parser.parse(locationString);

        assertNotSame(location1, location2);;
    }

    @Test
    void testParse_eachAttributeAtOnceOk(){
        Location location = parser.parse(locationString);
        assertAll(
            () -> assertEquals("Budapest", location.getName()),
            () -> assertEquals(47.497912, location.getLatitude()),
            () -> assertEquals(19.040235, location.getLongitude())
        );
    }
}
