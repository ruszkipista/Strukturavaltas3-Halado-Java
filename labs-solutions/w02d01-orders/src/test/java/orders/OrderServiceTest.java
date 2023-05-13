package orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    Order order;
    long orderId = 3L;
    String productName = "Prod3";

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void init() {
        order = new Order(orderId, productName, 3, 13);
    }

    @Test
    void testSaveOrder(){
        when(orderRepository.saveOrder(any())).thenReturn(orderId);

        long orderId = orderService.saveOrder(order);

        assertEquals(orderId, orderId);
        verify(orderRepository).saveOrder(order);
    }

    @Test
    void testsaveOrderAndDontReturnGeneratedKeys() {
        orderService.saveOrderAndDontReturnGeneratedKeys(order);

        verify(orderRepository).saveOrder(order);
    }

    @Test
    void testGetOrderById(){
        when(orderRepository.getOrderById(orderId))
            .thenReturn(Optional.of(order));

        Optional<Order> orderHolder = orderService.getOrderById(orderId);

        assertEquals(true, orderHolder.isPresent());
        Order order = orderHolder.get();
        assertEquals(orderId, order.getId());
        assertEquals(productName, order.getProductName());
        verify(orderRepository).getOrderById(orderId);
    }

    @Test
    void testGetOrders(){
        when(orderRepository.getOrders()).thenReturn(List.of(
            new Order("Prod1", 3, 5),
            new Order("Prod4", 13, 19),
            order
        ));

        List<Order> orders = orderService.getOrders();

        assertEquals(3, orders.size());
        verify(orderRepository).getOrders();
    }

    @Test
    void testGetOrdersOverLimitedOrderPrice_returnsList() {
        int orderValueLimit = 180;
        when(orderRepository.getOrdersOverLimitedOrderPrice(orderValueLimit)).thenReturn(List.of(
                new Order("Prod1", 11, 17),
                new Order("Prod2", 13, 19)));

        List<Order> orders = orderService.getOrdersOverValueLimit(orderValueLimit);

        Assertions.assertThat(orders)
                .hasSize(2)
                .extracting(Order::getProductName)
                .containsExactly("Prod1", "Prod2");

        verify(orderRepository).getOrdersOverLimitedOrderPrice(orderValueLimit);
    }

    @Test
    void testGetOrdersOverLimitedOrderPrice_fails() {
        int orderValueLimit = 999_999;
        when(orderRepository.getOrdersOverLimitedOrderPrice(orderValueLimit)).thenReturn(List.of());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> orderService.getOrdersOverValueLimit(orderValueLimit));
        assertEquals("Too high limit", ex.getMessage());
        verify(orderRepository).getOrdersOverLimitedOrderPrice(orderValueLimit);
    }

    @Test
    void testCollectProductsAndCount() {
        when(orderRepository.getOrders()).thenReturn(List.of(
            new Order("Prod1", 3, 5),
            new Order("Prod2", 5, 23),
            new Order("Prod4", 13, 19),
            new Order("Prod2", 7, 23)
        ));

        Map<String, Integer> result = orderService.collectProductsAndCount();

        Assertions.assertThat(result)
            .containsExactlyInAnyOrderEntriesOf(
                Map.of("Prod1", 3, 
                        "Prod2", 12, 
                        "Prod4", 13)
            );
      
        verify(orderRepository).getOrders();
    }
}