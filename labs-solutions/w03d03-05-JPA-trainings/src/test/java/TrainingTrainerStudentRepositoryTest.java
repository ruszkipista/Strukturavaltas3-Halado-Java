import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainingTrainerStudentRepositoryTest {
    EntityManagerFactory entityManagerFactory;
    TrainingRepository repo;

    @BeforeEach
    void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        repo = new TrainingRepository(entityManagerFactory);
    }

    @AfterEach
    void close(){
        entityManagerFactory.close();
    }

    @Test
    void saveTraining(){
        Training training = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-03-31"));
        repo.save(training);

        Training trainingCopy = repo.findTrainingById(training.getId());
        assertEquals("feng shui", trainingCopy.getTitle());
    }

}
