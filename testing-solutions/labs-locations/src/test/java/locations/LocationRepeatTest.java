package locations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

public class LocationRepeatTest {
    double[][] parameters = {{1,1,0},{0,1,1},{0,0,1},{1,0,0}};

    @RepeatedTest(value = 4, name = "{currentRepetition} / {totalRepetitions}")
    void testEquator(RepetitionInfo info){
        int repIndex = info.getCurrentRepetition()-1;
        Location location = new Location("Loc", parameters[repIndex][0], parameters[repIndex][1]);

        boolean expected = (parameters[repIndex][2] == 1) ? true : false;
        assertEquals(expected, location.isOnEquator());
    }
}
