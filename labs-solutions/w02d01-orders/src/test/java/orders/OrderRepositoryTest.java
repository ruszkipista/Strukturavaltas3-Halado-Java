package orders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mariadb.jdbc.MariaDbDataSource;

class OrderRepositoryTest {

    OrderRepository orderRepository;

    @BeforeEach
    void init() {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/orders?useUnicode=true");
            dataSource.setUser("training");
            dataSource.setPassword("training");
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot connect!", se);
        }

        Flyway flyway = Flyway.configure()
                .cleanDisabled(false)
                .dataSource(dataSource)
                .load();
        flyway.clean();
        flyway.migrate();

        orderRepository = new OrderRepository(dataSource);
    }

    @Test
    void testGetOrderById_notFound(){
        assertEquals(true, orderRepository.getOrderById(1L).isEmpty());
    }

    @Test
    void testSaveOrder() {
        Order orderOriginal = new Order("Prod1", 3, 5);

        long orderId = orderRepository.saveOrder(orderOriginal);
        Order orderRead = orderRepository.getOrderById(orderId).get();

        assertEquals(orderId, orderRead.getId());
        assertEquals(orderOriginal.getProductName(), orderRead.getProductName());
    }

    @Test
    void testGetOrders() {
        List<Long> ids = new ArrayList<>();
        ids.add(orderRepository.saveOrder(new Order("Prod1", 3, 13)));
        ids.add(orderRepository.saveOrder(new Order("Prod4", 5, 7)));
        ids.add(orderRepository.saveOrder(new Order("Prod2", 7, 11)));
        ids.add(orderRepository.saveOrder(new Order("Prod3", 11, 5)));

        List<Order> orders = orderRepository.getOrders();

        assertThat(orders)
                .hasSize(4)
                .extracting(Order::getProductName)
                .containsExactly("Prod1", "Prod2", "Prod3", "Prod4");
    }

    @ParameterizedTest
    @MethodSource("createArguments")
    void testOrdersOverTotalValueLimit(int limit, int numberOfOrders) {
        List<Long> ids = new ArrayList<>();
        ids.add(orderRepository.saveOrder(new Order("Prod1", 3, 13)));
        ids.add(orderRepository.saveOrder(new Order("Prod4", 5, 7)));
        ids.add(orderRepository.saveOrder(new Order("Prod2", 7, 11)));
        ids.add(orderRepository.saveOrder(new Order("Prod3", 11, 5)));

        assertEquals(numberOfOrders, orderRepository.getOrdersOverLimitedOrderPrice(limit).size());
    }

    static Stream<Arguments> createArguments() {
        return Stream.of(
            Arguments.arguments(60, 1),
            Arguments.arguments(50, 2),
            Arguments.arguments(35, 3),
            Arguments.arguments(30, 4)
        );
    }

}