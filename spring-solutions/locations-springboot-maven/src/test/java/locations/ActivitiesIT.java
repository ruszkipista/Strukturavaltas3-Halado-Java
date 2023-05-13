package locations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import locations.controller.ActivitiesController;

@SpringBootTest
public class ActivitiesIT {
    @Autowired
    ActivitiesController controller;

	@Test
	void contextLoads() {
	}

    @Test
    void getLocations_getList() throws IOException{
        assertEquals("[Running, Swimming, Yoga, Reading]", controller.listActivities());
    }

}
