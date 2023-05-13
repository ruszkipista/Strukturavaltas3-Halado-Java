package flights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import flights.model.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}
