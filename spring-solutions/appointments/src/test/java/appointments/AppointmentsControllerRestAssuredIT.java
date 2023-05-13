package appointments;

import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import appointments.model.Appointment;
import appointments.model.AppointmentCreateCommand;
import appointments.model.CaseType;
import appointments.repository.AppointmentsRepository;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentsControllerRestAssuredIT {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  AppointmentsRepository repo;

  LocalDateTime tomorrowNow = LocalDateTime.now().plusDays(1);

  @BeforeEach
  void init(){
    repo.clear();
    repo.saveAppointment(new Appointment(1234567890L, tomorrowNow.plusMinutes(30), tomorrowNow.plusMinutes(60), CaseType.C001));
    repo.saveAppointment(new Appointment(1234567891L, tomorrowNow.plusMinutes(60), tomorrowNow.plusMinutes(90), CaseType.C002));
    repo.saveAppointment(new Appointment(1234567892L, tomorrowNow.plusMinutes(90), tomorrowNow.plusMinutes(120), CaseType.C001));

    RestAssuredMockMvc.mockMvc(mockMvc);
    RestAssuredMockMvc.requestSpecification = RestAssuredMockMvc.given()
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON);
  }

  @Test
  void testReadAll_success() {
    RestAssuredMockMvc.when()
        .get("/appointments")
        .then()
          .statusCode(200)
          .body("[0].personalTaxId", equalTo(1234567890))
          .body("[1].personalTaxId", equalTo(1234567891))
          .body("[2].personalTaxId", equalTo(1234567892));
  }

  @Test
  void getAppointmentById_found() {
    RestAssuredMockMvc.when()
        .get("/appointments/2")
        .then()
          .statusCode(200)
          .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("appointment-dto.json"))
          .body("personalTaxId", equalTo(1234567891));
  }

  @Test
  void getAppointmentById_notFound() {
    RestAssuredMockMvc.when()
        .get("/appointments/42")
        .then()
          .statusCode(equalTo(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  void createAppointment_createdWithId() {
    RestAssuredMockMvc.with()
          .body(new AppointmentCreateCommand(9999999999L, tomorrowNow.plusMinutes(90), tomorrowNow.plusMinutes(120), CaseType.C001))
        .post("/appointments")
        .then()
          .statusCode(201)
          .body("personalTaxId", equalTo(9999999999L));
  }

}
