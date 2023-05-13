package locations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class LocationDynamicTest {
    List<Location> locations;
    boolean[] expected;

    @BeforeEach
    void init(){
        locations = new ArrayList<>();
        locations.add(new Location("Budapest", 47.49791, 19.04023));
        locations.add(new Location("Debrecen", 47.52997, 21.63916));
        locations.add(new Location("Afrika", 0.0, 20.0));
        expected = new boolean[] {false,false,true};
    }

    @TestFactory
    Stream<DynamicTest> testIsOnEquator(){
        return IntStream.range(0, locations.size())
            .mapToObj(i -> DynamicTest.dynamicTest(locations.get(i).getName(), 
                        ()->assertEquals(expected[i], locations.get(i).isOnEquator())));
    }
}
