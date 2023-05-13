package locations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import locations.model.Location;
import locations.repository.LocationsRepository;

@DataJpaTest
@Sql(statements = {
    "TRUNCATE TABLE locations RESTART IDENTITY;",
    "INSERT INTO locations (location_name,latitude,longitude) VALUES ('Aaa',0,0),('Bbb',0,1),('Ccc',1,0),('Ddd',1,1),('Eee',-1,1);"
})
public class LocationsRepositoryTest {
    @Autowired
    LocationsRepository repo;
    
    @Test
    void readAllLocations() {
        assertThat(repo.findAll())
            .hasSize(5)
            .extracting(Location::getName)
            .contains("Aaa", "Ccc")
            .doesNotContain("Xxx");
    }

    @Test
    void readLocationById() {
        assertEquals("Bbb", repo.findById(2L).get().getName());
    }

    @Test
    void updateLocationById() {
        Location location = new Location(2L,"Zzz", 1.0, 2.0);

        repo.save(location);

        assertThat(repo.findAll())
            .hasSize(5)
            .extracting(Location::getName)
            .containsOnly("Aaa", "Zzz", "Ccc", "Ddd", "Eee")
            .doesNotContain("Bbb");
    }

    @Test
    void deleteLocationById() {
        repo.deleteById(2L);
        assertThat(repo.findAll())
            .hasSize(4)
            .extracting(Location::getName)
            .containsOnly("Aaa", "Ccc", "Ddd", "Eee")
            .doesNotContain("Bbb");
    }
}
