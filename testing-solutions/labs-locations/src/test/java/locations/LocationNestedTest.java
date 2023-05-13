package locations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LocationNestedTest {
    LocationParser parser;

    @BeforeEach
    void initParser(){
        parser = new LocationParser();
    }
    
    @Nested
    class LocationZeroZero {
        Location location;

        @BeforeEach
        void initLocation(){
            location = new Location("Loc", 0.0, 0.0);
        }

        @Test
        void testEquator(){
            assertEquals(true, location.isOnEquator());
        }
    
        @Test
        void testPrimeMeridian(){
            assertEquals(true, location.isOnPrimeMeridian());
        }
    }

    @Nested
    class LocationBudapest {
        Location location;

        @BeforeEach
        void initLocation(){
            location = new Location("Loc", 47.497912,19.040235);
        }

        @Test
        void testEquator(){
            assertEquals(false, location.isOnEquator());
        }
    
        @Test
        void testPrimeMeridian(){
            assertEquals(false, location.isOnPrimeMeridian());
        }
    }
}
