package locations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import locations.model.Location;

@Repository
public interface LocationsRepository extends JpaRepository<Location, Long> {

    @Query("select l from Location l where upper(l.name) like upper(:name)")
    List<Location> findAllByPrefix(String name);
 
}
