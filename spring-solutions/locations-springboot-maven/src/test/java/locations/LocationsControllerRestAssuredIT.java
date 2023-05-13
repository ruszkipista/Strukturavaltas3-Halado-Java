package locations;

import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import locations.model.Location;
import locations.model.LocationCreateCommand;
import locations.model.LocationUpdateCommand;
import locations.repository.LocationsRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationsControllerRestAssuredIT {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  LocationsRepository repo;

  List<Location> locations;

  @BeforeEach
  void init(){
      repo.deleteAll();
      locations = repo.saveAll(List.of(
          new Location("Aaa",0,0),
          new Location("Bbb",0,1),
          new Location("Ccc",1,0),
          new Location("Ddd",1,1),
          new Location("Eee",-1,1)
      ));

    RestAssuredMockMvc.mockMvc(mockMvc);
    RestAssuredMockMvc.requestSpecification = RestAssuredMockMvc.given()
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON);
  }

  @Test
  void testReadAll_success() {
    RestAssuredMockMvc.when()
        .get("/locations")
        .then()
          .statusCode(200)
          .body("[0].name", equalTo("Aaa"))
          .body("[4].name", equalTo("Eee"));
  }

  @Test
  void testReadFilteredByName_success() {
    RestAssuredMockMvc.when()
        .get("/locations?name=Eee")
        .then()
          .statusCode(200)
          .body("[0].name", equalTo("Eee"));
  }

  @Test
  void getLocationsWithinLimit_allReturned() {
    RestAssuredMockMvc.when()
        .get("/locations?minLat=0&maxLat=2&minLon=0&macLon=2")
        .then()
          .statusCode(200)
          .body("size()", equalTo(5))
          .body("[0].name", equalTo("Aaa"))
          .body("[4].name", equalTo("Eee"));  }

  @Test
  void getLocationsWithinLimit_oneReturned() {
    RestAssuredMockMvc.when()
        .get("/locations-within-limit?minLat=0&maxLat=0&minLon=0&maxLon=0")
        .then()
          .statusCode(200)
          .body(equalTo("[Aaa]"));
  }

  @Test
  void getLocationById_found() {
    Long id = locations.get(1).getId();

    RestAssuredMockMvc.when()
        .get("/locations/{id}", id)
        .then()
          .statusCode(200)
          .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("location-dto.json"))
          .body("name", equalTo("Bbb"));
  }

  @Test
  void getLocationById_notFound() {
    RestAssuredMockMvc.when()
        .get("/locations/99999")
        .then()
          .statusCode(equalTo(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  void createLocation_createdWithId() {
    RestAssuredMockMvc.with()
          .body(new LocationCreateCommand("Zzz", -1, 1))
        .post("/locations")
        .then()
          .statusCode(201)
          .body(org.hamcrest.Matchers.endsWith(",\"name\":\"Zzz\"}"));

    RestAssuredMockMvc.when().delete("/locations/6");
  }

  @Test
  void updateLocationById_updatedName() {
    Long id = locations.get(3).getId();

    RestAssuredMockMvc.with() 
          .body(new LocationUpdateCommand("Yyy", 0.0, 1.0))
        .put("/locations/{id}", id)
        .then()
          .statusCode(200)
          .body(org.hamcrest.Matchers.endsWith(",\"name\":\"Yyy\"}"));

    RestAssuredMockMvc.when()
        .get("/locations/{id}", id)
        .then()
          .statusCode(200)
          .body("name", equalTo("Yyy"));
  }

  @Test
  void deleteLocationById_deleted() {
    Long id = locations.get(2).getId();

    RestAssuredMockMvc.when()
        .delete("/locations/{id}", id)
        .then()
          .statusCode(204)
          .body(equalTo(""));

    RestAssuredMockMvc.when()
        .get("/locations/{id}", id)
        .then()
          .statusCode(404);
  }

  @Test
  void updateLocationById_outOfRangeLatitudeLongitude_notValid() {
    RestAssuredMockMvc.with()
          .body(new LocationUpdateCommand("Yyy", -91, 180))
        .put("/locations/9999")
        .then()
          .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
          .body("status", equalTo(HttpStatus.NOT_ACCEPTABLE.value()))
          .body("type", equalTo("locations/request-not-valid"))
          .body("title", equalTo("Not Acceptable"))
          .body("detail", Matchers.startsWith("Validation failed for "))
          .body("violations[0].field", equalTo("latitude"));
  }

  @Test
  void updateLocationById_notFound() {
    RestAssuredMockMvc.with()
          .body(new LocationUpdateCommand("Yyy", 0, 0))
        .put("/locations/9999")
        .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("status", equalTo(HttpStatus.NOT_FOUND.value()))
          .body("type", equalTo("locations/location-not-found"))
          .body("title", equalTo("Not Found"))
          .body("detail", equalTo("Unable to find location to update 9999"));
  }


  @Test
  void deleteLocationById_notFound() {
    RestAssuredMockMvc.when()
        .delete("/locations/9999")
        .then()
          .statusCode(HttpStatus.NO_CONTENT.value());
  }
}
