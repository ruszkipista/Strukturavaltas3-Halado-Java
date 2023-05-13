package training360.gardenservices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import training360.gardenservices.dto.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from gardenworks", "delete from gardeners"})
class GardenServicesIT {

    @Autowired
    TestRestTemplate template;

    @Test
    void testCreateAndListGardener() {
        CreateGardenerCommand command = new CreateGardenerCommand("John Doe");
        GardenerDto gardener = template.postForObject("/api/gardeners", command, GardenerDto.class);

        assertEquals("John Doe", gardener.getName());

        List<GardenerDto> members = template.exchange("/api/gardeners",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GardenerDto>>(){}).getBody();
        assertThat(members)
                .extracting(GardenerDto::getName)
                .containsExactly("John Doe");
    }

    @Test
    void testCreateAndListGardenWork() {
        CreateGardenerCommand createGardenerCommand = new CreateGardenerCommand("John Doe");
        GardenerDto gardener = template.postForObject("/api/gardeners", createGardenerCommand, GardenerDto.class);

        CreateGardenWorkCommand command = new CreateGardenWorkCommand("Do the gardening in my garden.");
        GardenWorkDto gardenWork = template.postForObject("/api/gardeners/{id}/gardenworks", command, GardenWorkDto.class, gardener.getId());

        assertThat(gardenWork)
                .extracting(GardenWorkDto::getDescription, GardenWorkDto::isDone, GardenWorkDto::getAnswer)
                .containsSequence("Do the gardening in my garden.", false, null);

        List<GardenWorkDto> gardenWorks = template.exchange("/api/gardeners/{id}/gardenworks",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GardenWorkDto>>(){}, gardener.getId()).getBody();
        assertThat(gardenWorks)
                .extracting(GardenWorkDto::getDescription)
                .containsExactly("Do the gardening in my garden.");
    }

    @Test
    void testDoAndAnswerGardenWork() {
        CreateGardenerCommand createGardenerCommand = new CreateGardenerCommand("John Doe");
        GardenerDto gardener = template.postForObject("/api/gardeners", createGardenerCommand, GardenerDto.class);

        CreateGardenWorkCommand command = new CreateGardenWorkCommand("Do the gardening in my garden.");
        GardenWorkDto gardenWork = template.postForObject("/api/gardeners/{id}/gardenworks", command, GardenWorkDto.class, gardener.getId());

        GardenWorkDto answered = template.exchange("/api/gardeners/{gardenerId}/gardenworks/{gardenWorkId}/answer",
                HttpMethod.PUT,new HttpEntity(
                        new AnswerCommand("I have done it.")),
                GardenWorkDto.class, gardener.getId(), gardenWork.getId()).getBody();

        assertThat(answered)
                .extracting(GardenWorkDto::getDescription, GardenWorkDto::isDone, GardenWorkDto::getAnswer)
                .containsSequence("Do the gardening in my garden.", true, "I have done it.");
    }

    @Test
    void testGetPairs() {
        CreateGardenerCommand createGardenerCommand = new CreateGardenerCommand("John Doe");
        GardenerDto gardener = template.postForObject("/api/gardeners", createGardenerCommand, GardenerDto.class);

        CreateGardenWorkCommand command;

        command = new CreateGardenWorkCommand("gardenwork1");
        template.postForObject("/api/gardeners/{id}/gardenworks", command, GardenWorkDto.class, gardener.getId());

        command = new CreateGardenWorkCommand("gardenwork2");
        template.postForObject("/api/gardeners/{id}/gardenworks", command, GardenWorkDto.class, gardener.getId());

        createGardenerCommand = new CreateGardenerCommand("Jack Doe");
        GardenerDto gardener2 = template.postForObject("/api/gardeners", createGardenerCommand, GardenerDto.class);

        command = new CreateGardenWorkCommand("gardenwork3");
        template.postForObject("/api/gardeners/{id}/gardenworks", command, GardenWorkDto.class, gardener2.getId());

        command = new CreateGardenWorkCommand("gardenwork4");
        GardenWorkDto gardenWork = template.postForObject("/api/gardeners/{id}/gardenworks", command, GardenWorkDto.class, gardener2.getId());

        template.exchange("/api/gardeners/{gardenerId}/gardenworks/{gardenWorkId}/answer",
                HttpMethod.PUT,new HttpEntity(
                        new AnswerCommand("answer4")),
                GardenWorkDto.class, gardener2.getId(), gardenWork.getId()).getBody();


        List<NameDescriptionPairDto> namesAndDescriptions = template.exchange("/api/pairs",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<NameDescriptionPairDto>>(){}).getBody();
        assertThat(namesAndDescriptions)
                .extracting(NameDescriptionPairDto::getName, NameDescriptionPairDto::getDescription)
                .containsExactly(
                        tuple("Jack Doe", "gardenwork3"),
                        tuple("John Doe", "gardenwork1"),
                        tuple("John Doe", "gardenwork2")
                );
    }
}