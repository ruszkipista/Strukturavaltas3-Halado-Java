package flights;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import flights.dtos.AirplaneDto;
import flights.dtos.CreateAirplaneCommand;
import flights.dtos.CreateRouteCommand;
import flights.dtos.RouteDto;
import flights.model.AirplaneType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        "SET REFERENTIAL_INTEGRITY false; ",
        "TRUNCATE TABLE routes RESTART IDENTITY; ",
        "TRUNCATE TABLE airplanes RESTART IDENTITY; ",
        "SET REFERENTIAL_INTEGRITY true;"
})
class AirplaneRouteControllerTest {

    @Autowired
    WebTestClient webClient;

    AirplaneDto airplane;

    ProblemDetail problem;

    LocalDate date = LocalDate.now().plusDays(1L);

    @BeforeEach
    void init() {
        airplane = webClient.post()
                .uri("api/airplanes")
                .bodyValue(new CreateAirplaneCommand(AirplaneType.BOEING_787, "Lufthansa"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AirplaneDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testSaveAirplane() {
        assertNotNull(airplane.getId());
        assertEquals(AirplaneType.BOEING_787, airplane.getAirplaneType());
        assertEquals("Lufthansa", airplane.getOwnerAirline());
        assertTrue(airplane.getRoutes().isEmpty());
    }

    @Test
    void testSaveAirplaneWithoutAirplaneType() {
        AirplaneDto plane = webClient.post()
                .uri("api/airplanes")
                .bodyValue(new CreateAirplaneCommand(null, "Lufthansa"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AirplaneDto.class)
                .returnResult().getResponseBody();

        assertNotNull(plane.getId());
        assertNull(plane.getAirplaneType());
        assertEquals("Lufthansa", plane.getOwnerAirline());
        assertTrue(plane.getRoutes().isEmpty());
    }

    @Test
    void testSaveAirplaneWithoutOwnerAirline() {
        problem = webClient.post()
                .uri("api/airplanes")
                .bodyValue(new CreateAirplaneCommand(AirplaneType.BOEING_787, null))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("airplanes/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testSaveAirplaneWithEmptyOwnerAirline() {
        problem = webClient.post()
                .uri("api/airplanes")
                .bodyValue(new CreateAirplaneCommand(AirplaneType.BOEING_787, ""))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("airplanes/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testSaveRouteToAirplane() {
        RouteDto route = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange()
                .expectBody(RouteDto.class)
                .returnResult().getResponseBody();

        assertNotNull(route.getId());
        assertEquals("Budapest", route.getDepartureCity());
        assertEquals("London", route.getArrivalCity());
        assertEquals(date, route.getDateOfFlight());
    }

    @Test
    void testSaveRouteToNotFoundAirplane() {
        long id = airplane.getId() + 1000;
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(id))
                .bodyValue(new CreateRouteCommand("Budapest", "London", LocalDate.now().plusDays(1L)))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("airplanes/not-found"), problem.getType());
        assertEquals("Airplane not found with id:" + id, problem.getDetail());
    }

    @Test
    void testSaveRouteToAirplaneWithoutDepartureCity() {
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand(null, "London", LocalDate.now().plusDays(1L)))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("airplanes/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testSaveRouteToAirplaneWithEmptyDepartureCity() {
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("", "London", LocalDate.now().plusDays(1L)))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("airplanes/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testSaveRouteToAirplaneWithoutArrivalCity() {
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", null, LocalDate.now().plusDays(1L)))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("airplanes/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testSaveRouteToAirplaneWithEmptyArrivalCity() {
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "", LocalDate.now().plusDays(1L)))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("airplanes/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testSaveRouteToAirplaneWithDateInThePast() {
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", LocalDate.now().minusDays(1L)))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("airplanes/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testSaveRouteToAirplaneWithSameDate() {
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange()
                .expectBody(RouteDto.class)
                .returnResult().getResponseBody();
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("airplanes/not-valid"), problem.getType());
        assertEquals("Flight is not free on " + date, problem.getDetail());
    }

    @Test
    void testFindAllAirplanesByAirline() {
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date.plusDays(1L)))
                .exchange();
        AirplaneDto plane = webClient.post()
                .uri("api/airplanes")
                .bodyValue(new CreateAirplaneCommand(AirplaneType.BOEING_787, "Malév"))
                .exchange()
                .expectBody(AirplaneDto.class)
                .returnResult().getResponseBody();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(plane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(plane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date.plusDays(1L)))
                .exchange();
        AirplaneDto other = webClient.post()
                .uri("api/airplanes")
                .bodyValue(new CreateAirplaneCommand(AirplaneType.BOEING_787, "Lufthansa"))
                .exchange()
                .expectBody(AirplaneDto.class)
                .returnResult().getResponseBody();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(other.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange();

        webClient.get()
                .uri("api/airplanes")
                .exchange()
                .expectBodyList(AirplaneDto.class)
                .hasSize(3);

        webClient.get()
                .uri("api/airplanes?ownerAirline=Lufthansa")
                .exchange()
                .expectBodyList(AirplaneDto.class)
                .hasSize(2);

        List<AirplaneDto> airplanes = webClient.get()
                .uri("api/airplanes?ownerAirline=Malév")
                .exchange()
                .expectBodyList(AirplaneDto.class)
                .returnResult().getResponseBody();

        assertThat(airplanes)
                .hasSize(1);
        Set<RouteDto> routes = airplanes.get(0).getRoutes();
        assertThat(routes)
                .hasSize(2);

        webClient.get()
                .uri("api/airplanes?ownerAirline=Luft")
                .exchange()
                .expectBodyList(AirplaneDto.class)
                .hasSize(0);
    }

    @Test
    void testCancelFlight() {
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange()
                .expectBody(RouteDto.class)
                .returnResult().getResponseBody();
        RouteDto route = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date.plusDays(1L)))
                .exchange()
                .expectBody(RouteDto.class)
                .returnResult().getResponseBody();

        AirplaneDto airplaneResult = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes/{routeId}").build(airplane.getId(), route.getId()))
                .exchange()
                .expectBody(AirplaneDto.class)
                .returnResult().getResponseBody();

        assertThat(airplaneResult.getRoutes())
                .hasSize(1)
                .extracting(RouteDto::getDateOfFlight)
                .containsExactly(date);
    }

    @Test
    void testCancelFlightWithWrongAirplane() {
        long wrongId = airplane.getId() + 1;
        problem = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes/{routeId}").build(wrongId, wrongId))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals("Airplane not found with id:" + wrongId, problem.getDetail());
    }

    @Test
    void testCancelFlightWithWrongRoute() {
        long wrongId = airplane.getId() + 1;
        problem = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes/{routeId}").build(airplane.getId(), wrongId))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals("Route not found with id: " + wrongId, problem.getDetail());
    }

    @Test
    void testCancelFlightWithRouteNotBelongsToAirplane() {
        AirplaneDto otherPlane = webClient.post()
                .uri("api/airplanes")
                .bodyValue(new CreateAirplaneCommand(AirplaneType.BOEING_747, "KLM"))
                .exchange()
                .expectBody(AirplaneDto.class)
                .returnResult().getResponseBody();

        RouteDto wrongRoute = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange().expectBody(RouteDto.class).returnResult().getResponseBody();

        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(otherPlane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange().expectBody(RouteDto.class).returnResult().getResponseBody();

        problem = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes/{routeId}").build(otherPlane.getId(), wrongRoute.getId()))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();

        assertEquals("Route not found with id: " + wrongRoute.getId(), problem.getDetail());
    }

    @Test
    void testCancelFlightWithRouteAlreadyCanceled() {
        RouteDto route = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes").build(airplane.getId()))
                .bodyValue(new CreateRouteCommand("Budapest", "London", date))
                .exchange()
                .expectBody(RouteDto.class)
                .returnResult().getResponseBody();

        webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes/{routeId}").build(airplane.getId(), route.getId()))
                .exchange()
                .expectBody(AirplaneDto.class)
                .returnResult().getResponseBody();

        problem = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/airplanes/{id}/routes/{routeId}").build(airplane.getId(), route.getId()))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
    }
}