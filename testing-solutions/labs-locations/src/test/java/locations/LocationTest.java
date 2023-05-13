package locations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LocationTest {
    LocationParser parser;
    Location locBudapest = new Location("Budapest", 47.49791, 19.04023);
    Location locDebrecen = new Location("Debrecen", 47.52997, 21.63916);

    @BeforeEach
    void initParser() {
        parser = new LocationParser();
    }

    @Disabled
    @Test
    @DisplayName("This test case is intentionally left empty and prevented from execution")
    void doesNotRun() {
    }

    @Test
    @DisplayName("Location is on the Equator")
    void testOnEquator01() {
        Location location = new Location("OnTheEquator", 0, 1);
        assertEquals(true, location.isOnEquator());
    }

    @Test
    @DisplayName("Location is Not on the Equator, but on the Prime Meridian")
    void testNotOnEquator10() {
        Location location = new Location("NotOnTheEquator", 1, 0);
        assertEquals(false, location.isOnEquator());
    }

    @Test
    @DisplayName("Location is Not on the Equator")
    void testNotOnEquator11() {
        Location location = new Location("NotOnTheEquator", 1, 1);
        assertEquals(false, location.isOnEquator());
    }

    @Test
    @DisplayName("Location is on the Prime Meridian")
    void testOnPrimeMeridian10() {
        Location location = new Location("OnThePrimeMeridian", 1, 0);
        assertEquals(true, location.isOnPrimeMeridian());
    }

    @Test
    @DisplayName("Location is Not on the Prime Meridian, but on the Equator")
    void testNotOnPrimeMeridian01() {
        Location location = new Location("NotOnThePrimeMeridian", 0, 1);
        assertEquals(false, location.isOnPrimeMeridian());
    }

    @Test
    @DisplayName("Location is Not on the Prime Meridian")
    void testNotOnPrimeMeridian11() {
        Location location = new Location("NotOnThePrimeMeridian", 1, 1);
        assertEquals(false, location.isOnPrimeMeridian());
    }

    @Test
    void distanceOfBudapestDebrecen_givenWithDelta() {
        assertEquals(195.2, locBudapest.distanceFrom(locDebrecen), 0.01);
    }

    @Test
    void distanceOfSame_Zero() {
        assertEquals(0.0, locBudapest.distanceFrom(locBudapest));
    }

    @Test
    void testDifferentLocations(){
        Location locBudapest1 = new Location("Budapest", 47.49791, 19.04023);
        Location locBudapest2 = new Location("Budapest", 47.49791, 19.04023);

        assertAll(
            ()->assertEquals(locBudapest1, locBudapest2),
            ()->assertNotSame(locBudapest1, locBudapest2)
        );
    }

    @Test
    void testCreate_allThrows(){
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, ()->new Location(null, 0, 0)),
            () -> assertThrows(IllegalArgumentException.class, ()->new Location("", 0, 0)),
            () -> assertThrows(IllegalArgumentException.class, ()->new Location(" \t", 0, 0)),
            () -> assertThrows(IllegalArgumentException.class, ()->new Location("loc", -90.1, 0)),
            () -> assertThrows(IllegalArgumentException.class, ()->new Location("loc", 91.1, 0)),
            () -> assertThrows(IllegalArgumentException.class, ()->new Location("loc", 0, -180.1)),
            () -> assertThrows(IllegalArgumentException.class, ()->new Location("loc", 0, 180.1))
        );
    }
}
