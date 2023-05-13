package flights.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import flights.model.Airplane;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    @Query("SELECT a FROM Airplane a WHERE :ownerAirline IS NULL OR a.ownerAirline = :ownerAirline")
    List<Airplane> findAllByOwnerAirline(Optional<String> ownerAirline);
}
