import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AreaDaoIT {
    EntityManagerFactory entityManagerFactory;
    AreaDao repoArea;
    Area area;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        repoArea = new AreaDao(entityManagerFactory);
        area = new Area("Csillaghegy");
    }

    @AfterEach
    void close() {
        entityManagerFactory.close();
    }

    @Test
    void saveOneArea() {
        repoArea.saveArea(area);
        assertNotNull(area.getId());
    }

    @Test
    void readOneArea() {
        repoArea.saveArea(area);
        Area AreaCopy = repoArea.findAreaById(area.getId());
        assertEquals(AreaCopy.getName(), area.getName());
    }

    @Test
    void createCities() {
        repoArea.saveArea(area);
        area = new Area("Bekes megye");
        repoArea.saveArea(area);

        City cityBekes = repoArea.saveCity(new City("Bekes", 20_000, area));
        City citySarkad = repoArea.saveCity(new City("Sarkad", 6_000, area));
        City cityMurony = repoArea.saveCity(new City("Murony", 2_000, area));

        Area areaCopy = repoArea.getAreaByIdWithCities(area.getId());
        Assertions.assertThat(areaCopy.getCities())
                .hasSize(3)
                .contains(Assertions.entry("Murony", cityMurony));
    }
}
