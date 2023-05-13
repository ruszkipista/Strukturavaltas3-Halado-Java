package orders;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class OrderRepository {

    private JdbcTemplate jdbcTemplate;

    public OrderRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long saveOrder(Order order) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO orders (product_name,product_count, price_per_product) VALUES(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getProductName());
            ps.setInt(2, order.getProductCount());
            ps.setInt(3, order.getPricePerProduct());
            return ps;
        }, holder);

        return holder.getKey().longValue();
    }

    public Optional<Order> getOrderById(long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM orders WHERE id=?",
                    (rs, rn) -> Optional.of(new Order(rs.getLong("id"),
                                                      rs.getString("product_name"),
                                                      rs.getInt("product_count"),
                                                      rs.getInt("price_per_product"))),
                    id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Order> getOrders() {
        return jdbcTemplate.query(
                "SELECT * FROM orders ORDER BY product_name",
                (rs, rn) -> new Order(rs.getLong("id"),
                                      rs.getString("product_name"),
                                      rs.getInt("product_count"),
                                      rs.getInt("price_per_product")));
    }

    public List<Order> getOrdersOverLimitedOrderPrice(int lowerPriceLimit) {
        return jdbcTemplate.query(
                "SELECT * FROM orders WHERE (product_count*price_per_product) > ?",
                (rs, rn) -> new Order(rs.getLong("id"),
                                      rs.getString("product_name"),
                                      rs.getInt("product_count"),
                                      rs.getInt("price_per_product")),
                lowerPriceLimit);
    }
}
