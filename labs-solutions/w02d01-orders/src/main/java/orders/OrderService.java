package orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> getOrderById(long id) {
        return orderRepository.getOrderById(id);
    }

    public long saveOrder(Order order) {
        return orderRepository.saveOrder(order);
    }

    public void saveOrderAndDontReturnGeneratedKeys(Order order) {
        orderRepository.saveOrder(order);
    }

    public List<Order> getOrders() {
        return orderRepository.getOrders();
    }

    public List<Order> getOrdersOverValueLimit(int limit) {
        List<Order> ordersOverLimit = orderRepository.getOrdersOverLimitedOrderPrice(limit);
        if (ordersOverLimit.isEmpty()) {
            throw new IllegalArgumentException("Too high limit");
        }
        return ordersOverLimit;
    }

    public Map<String, Integer> collectProductsAndCount() {
        Map<String, Integer> result = new HashMap<>();
        List<Order> orders = orderRepository.getOrders();

        for (Order order : orders) {
            if (!result.containsKey(order.getProductName())) {
                result.put(order.getProductName(), 0);
            }
            result.put(order.getProductName(), result.get(order.getProductName()) + order.getProductCount());
        }
        return result;
    }

}

