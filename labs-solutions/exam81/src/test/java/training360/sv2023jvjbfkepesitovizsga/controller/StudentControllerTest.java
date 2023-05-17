package training360.sv2023jvjbfkepesitovizsga.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import training360.sv2023jvjbfkepesitovizsga.dtos.CreateStudentCommand;
import training360.sv2023jvjbfkepesitovizsga.dtos.CreateTestCommand;
import training360.sv2023jvjbfkepesitovizsga.dtos.StudentDto;
import training360.sv2023jvjbfkepesitovizsga.dtos.TestDto;
import training360.sv2023jvjbfkepesitovizsga.dtos.UpdateSchoolCommand;
import training360.sv2023jvjbfkepesitovizsga.model.School;
import training360.sv2023jvjbfkepesitovizsga.model.TestValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from tests", "delete from students"})
class StudentControllerTest {

    @Autowired
    WebTestClient webClient;

    StudentDto studentDto;

    ProblemDetail problem;

    @BeforeEach
    void init() {
        studentDto = webClient.post()
                .uri("api/students")
                .bodyValue(new CreateStudentCommand("John Doe", new School("SF High School", "San Fransisco")))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(StudentDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testSaveStudent() {
        assertNotNull(studentDto.getId());
        assertEquals("John Doe", studentDto.getName());
        assertEquals("SF High School", studentDto.getSchool().getSchoolName());
        assertEquals("San Fransisco", studentDto.getSchool().getCity());
    }

    @Test
    void testSaveStudentWithEmptyName() {
        problem = webClient.post()
                .uri("api/students")
                .bodyValue(new CreateStudentCommand("", new School("SF High School", "San Fransisco")))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("students/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testSavePerfectTestToStudent() {
        studentDto = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").build(studentDto.getId()))
                .bodyValue(new CreateTestCommand("Biology", 100, 76))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(StudentDto.class)
                .returnResult().getResponseBody();

        assertEquals(1, studentDto.getTests().size());
        assertNotNull(studentDto.getTests().get(0).getId());
        assertEquals("Biology", studentDto.getTests().get(0).getSubject());
        assertEquals(TestValue.PERFECT, studentDto.getTests().get(0).getTestValue());
    }

    @Test
    void testSaveAverageTestToStudent() {
        studentDto = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").build(studentDto.getId()))
                .bodyValue(new CreateTestCommand("Biology", 100, 51))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(StudentDto.class)
                .returnResult().getResponseBody();

        assertEquals(1, studentDto.getTests().size());
        assertNotNull(studentDto.getTests().get(0).getId());
        assertEquals("Biology", studentDto.getTests().get(0).getSubject());
        assertEquals(TestValue.AVERAGE, studentDto.getTests().get(0).getTestValue());
    }

    @Test
    void testSaveFailedTestToStudent() {
        studentDto = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").build(studentDto.getId()))
                .bodyValue(new CreateTestCommand("Biology", 100, 49))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(StudentDto.class)
                .returnResult().getResponseBody();

        assertEquals(1, studentDto.getTests().size());
        assertNotNull(studentDto.getTests().get(0).getId());
        assertEquals("Biology", studentDto.getTests().get(0).getSubject());
        assertEquals(TestValue.NOT_PASSED, studentDto.getTests().get(0).getTestValue());
    }

    @Test
    void testSaveTestToNotFoundStudent() {
        long wrongId = studentDto.getId() + 100;
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").build(wrongId))
                .bodyValue(new CreateTestCommand("Biology", 100, 49))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("students/not-found"), problem.getType());
        assertEquals("Student not found with id: " + wrongId, problem.getDetail());
    }

    @Test
    void testFindTestsBySubjectByStudent() {
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").build(studentDto.getId()))
                .bodyValue(new CreateTestCommand("Biology", 100, 76))
                .exchange();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").build(studentDto.getId()))
                .bodyValue(new CreateTestCommand("Biology", 100, 33))
                .exchange();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").build(studentDto.getId()))
                .bodyValue(new CreateTestCommand("Math", 100, 44))
                .exchange();

        StudentDto otherStudent = webClient.post()
                .uri("api/students")
                .bodyValue(new CreateStudentCommand("Jane Doe", new School("SF High School", "San Fransisco")))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(StudentDto.class)
                .returnResult().getResponseBody();

        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").build(otherStudent.getId()))
                .bodyValue(new CreateTestCommand("Biology", 100, 55))
                .exchange();


        List<TestDto> allTests = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").build(studentDto.getId()))
                .exchange()
                .expectBodyList(TestDto.class)
                .returnResult().getResponseBody();

        assertThat(allTests)
                .hasSize(3)
                .extracting(TestDto::getSubject)
                .contains("Biology", "Math");

        allTests = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").queryParam("subject", "Biology").build(studentDto.getId()))
                .exchange()
                .expectBodyList(TestDto.class)
                .returnResult().getResponseBody();

        assertThat(allTests)
                .hasSize(2);

        allTests = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}/tests").queryParam("subject", "History").build(studentDto.getId()))
                .exchange()
                .expectBodyList(TestDto.class)
                .returnResult().getResponseBody();

        assertTrue(allTests.isEmpty());

    }

    @Test
    void testStudentChangeSchool() {
        studentDto = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}").build(studentDto.getId()))
                .bodyValue(new UpdateSchoolCommand("LA High School", "Los Angeles"))
                .exchange()
                .expectBody(StudentDto.class)
                .returnResult().getResponseBody();

        assertEquals("LA High School", studentDto.getSchool().getSchoolName());
        assertEquals("Los Angeles", studentDto.getSchool().getCity());
    }

    @Test
    void testNotFoundStudentChangeSchool() {
        long wrongId = studentDto.getId() + 100;
        problem = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/students/{id}").build(wrongId))
                .bodyValue(new UpdateSchoolCommand("LA High School", "Los Angeles"))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("students/not-found"), problem.getType());
        assertEquals("Student not found with id: " + wrongId, problem.getDetail());
    }

}