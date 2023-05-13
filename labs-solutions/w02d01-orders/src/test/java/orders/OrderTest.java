package orders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void testCreationWithID(){
        Order order = new Order(1L, "Prod1", 3, 5);

        assertAll(
            () -> assertEquals(1L, order.getId()),
            () -> assertEquals("Prod1", order.getProductName()),
            () -> assertEquals(3, order.getProductCount()),
            () -> assertEquals(5, order.getPricePerProduct())
        );
    }

    @Test
    void testCreationWithoutID(){
        Order order = new Order("Prod1", 3, 5);

        assertAll(
            () -> assertEquals(null, order.getId()),
            () -> assertEquals("Prod1", order.getProductName()),
            () -> assertEquals(3, order.getProductCount()),
            () -> assertEquals(5, order.getPricePerProduct())
        );
    }
}