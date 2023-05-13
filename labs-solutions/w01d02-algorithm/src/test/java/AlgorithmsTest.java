import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class AlgorithmsTest {
    Algorithms algorithms = new Algorithms();

    @Test
    void testPickMultiplied_found(){
        List<Integer> numbers = List.of(4, 2,3,4,2,1);

        assertEquals(2, algorithms.getElementAppearsMoreThanOnce(numbers));
    }

    @Test
    void testBadInput_null(){
        assertThrows(IllegalArgumentException.class, ()->algorithms.getElementAppearsMoreThanOnce(null));
    }

    @Test
    void testBadInput_listSize1(){
        assertThrows(IllegalArgumentException.class, ()->algorithms.getElementAppearsMoreThanOnce(List.of(2)));
    }

    @Test
    void testBadInput_noRepeatElement(){
        assertThrows(IllegalArgumentException.class, ()->algorithms.getElementAppearsMoreThanOnce(List.of(2,3)));
    }


}
