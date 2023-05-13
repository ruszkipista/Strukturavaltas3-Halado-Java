import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainerTrainingRepositoryTest {
    EntityManagerFactory entityManagerFactory;
    TrainerRepository repoTr;
    TrainingRepository repoTg;
    StudentRepository repoSt;

    @BeforeEach
    void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        repoTr = new TrainerRepository(entityManagerFactory);
        repoTg = new TrainingRepository(entityManagerFactory);
        repoSt = new StudentRepository(entityManagerFactory);
    }

    @AfterEach
    void close(){
        entityManagerFactory.close();
    }

    @Test
    void save1Training_idIsNotNull(){
        Training training = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-03-31"));
        repoTg.save(training);

        assertNotNull(training.getId());
    }
    
    @Test
    void saveTrainer(){
        Trainer trainer = new Trainer("Oktato Joska", Trainer.ProfessionLevel.MEDIOR);
        repoTr.save(trainer);

        Trainer trainerCopy = repoTr.findTrainerById(trainer.getId());
        assertEquals("Oktato Joska", trainerCopy.getName());
    }

    @Test
    void saveTrainingWithTrainer(){
        Trainer trainer = new Trainer("Oktato Joska", Trainer.ProfessionLevel.MEDIOR);
        repoTr.save(trainer);
        Training training = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-03-31"));

        training.setTrainer(trainer);
        repoTg.save(training);

        Training trainingCopy = repoTg.findTrainingById(training.getId());
        assertNotNull(trainingCopy.getTrainer());
    }

    @Test
    void updateTrainingWithTrainer(){
        Trainer trainer = new Trainer("Oktato Joska", Trainer.ProfessionLevel.MEDIOR);
        repoTr.save(trainer);
        Training training = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-03-31"));
        repoTg.save(training);
        assertNull(training.getTrainer());

        repoTg.updateTrainingWithTrainer(training.getId(), trainer.getId());

        Training trainingCopy = repoTg.findTrainingById(training.getId());
        assertNotNull(trainingCopy.getTrainer());
    }

    @Test
    void updateTrainerWithTrainings(){
        Trainer trainer = new Trainer("Oktato Joska", Trainer.ProfessionLevel.MEDIOR);
        repoTr.save(trainer);

        Training training = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-03-31"), trainer);
        repoTg.save(training);

        repoTr.updateTrainerWithTraining(trainer.getId(), training.getId());;

        training = new Training("ike bana", LocalDate.parse("2023-04-01"), LocalDate.parse("2023-06-30"), trainer);
        repoTg.save(training);

        repoTr.updateTrainerWithTraining(trainer.getId(), training.getId());;

        Trainer trainerCopy = repoTr.findTrainerByIdWithTrainings(trainer.getId());
        assertEquals(2, trainerCopy.getTrainings().size());
    }

    @Test
    void findTrainerByIdWithTrainingsBetween(){
        Training training;
        Trainer trainer = new Trainer("Butito Peter", Trainer.ProfessionLevel.JUNIOR);
        repoTr.save(trainer);
        training = new Training("dummy1", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-31"));
        repoTg.save(training);
        repoTr.updateTrainerWithTraining(trainer.getId(), training.getId());
        training = new Training("dummy2", LocalDate.parse("2023-04-16"), LocalDate.parse("2023-06-30")); 
        repoTg.save(training);
        repoTr.updateTrainerWithTraining(trainer.getId(), training.getId());

        trainer = new Trainer("Oktato Joska", Trainer.ProfessionLevel.MEDIOR);
        repoTr.save(trainer);

        LocalDate intervalLow  = LocalDate.parse("2023-04-01");
        LocalDate intervalHigh = LocalDate.parse("2023-04-30");
        List<Long> trainings = new ArrayList<>();
        // s = trainig start, e = training end
        // L = interval low   H = interval high
        training = new Training("s-e-L-H", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-31"));
        repoTg.save(training);
        trainings.add(training.getId());
        training = new Training("L-H-s-e", LocalDate.parse("2023-05-01"), LocalDate.parse("2023-05-31"));
        repoTg.save(training);
        trainings.add(training.getId());
        training = new Training("s-L-e-H", LocalDate.parse("2023-02-01"), LocalDate.parse("2023-04-15"));
        repoTg.save(training);
        trainings.add(training.getId());
        training = new Training("L-s-H-e", LocalDate.parse("2023-04-16"), LocalDate.parse("2023-06-30"));
        repoTg.save(training);
        trainings.add(training.getId());
        training = new Training("L-s-e-H", LocalDate.parse("2023-04-10"), LocalDate.parse("2023-04-20"));
        repoTg.save(training);
        trainings.add(training.getId());
        training = new Training("s-L-H-e", LocalDate.parse("2023-02-01"), LocalDate.parse("2023-06-30"));
        repoTg.save(training);
        trainings.add(training.getId());
        training = new Training("s-eL--H", LocalDate.parse("2023-02-01"), LocalDate.parse("2023-04-01"));
        repoTg.save(training);
        trainings.add(training.getId());
        training = new Training("L--Hs-e", LocalDate.parse("2023-04-30"), LocalDate.parse("2023-06-30"));
        repoTg.save(training);
        trainings.add(training.getId());
        training = new Training("sL---eH", LocalDate.parse("2023-04-01"), LocalDate.parse("2023-04-30"));
        repoTg.save(training);
        trainings.add(training.getId());

        repoTr.updateTrainerWithTrainings(trainer.getId(), trainings);

        Trainer trainerCopy = repoTr.findTrainerByIdWithTrainingsBetween(trainer.getId(), intervalLow, intervalHigh);

        Assertions.assertThat(trainerCopy.getTrainings())
            .hasSize(7)
            .extracting(Training::getTitle)
            .containsExactlyInAnyOrder("s-L-e-H","L-s-H-e","L-s-e-H", "s-L-H-e", "s-eL--H", "L--Hs-e", "sL---eH");
    }


    @Test
    void findTrainerByIdWithTrainingsAndStudents(){
        Training training1 = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-31"));
        repoTg.save(training1);
        Training training2 = new Training("ike bana", LocalDate.parse("2023-04-16"), LocalDate.parse("2023-06-30")); 
        repoTg.save(training2);
        Training training3 = new Training("kara te", LocalDate.parse("2023-02-01"), LocalDate.parse("2023-02-28")); 
        repoTg.save(training3);
        Trainer trainer = new Trainer("Butito Peter", Trainer.ProfessionLevel.JUNIOR);
        repoTr.save(trainer);

        repoTr.updateTrainerWithTraining(trainer.getId(), training1.getId());
        repoTr.updateTrainerWithTraining(trainer.getId(), training2.getId());
        repoTr.updateTrainerWithTraining(trainer.getId(), training3.getId());

        Student student = new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE);
        repoSt.save(student);
        repoSt.updateStudentWithTraining(student.getId(), training1.getId());
        repoSt.updateStudentWithTraining(student.getId(), training3.getId());

        student = new Student("Szorgos Juli", LocalDate.parse("2021-01-01"), Student.ChargeType.FREE);
        repoSt.save(student);
        repoSt.updateStudentWithTraining(student.getId(), training1.getId());
        repoSt.updateStudentWithTraining(student.getId(), training2.getId());
        repoSt.updateStudentWithTraining(student.getId(), training3.getId());

        student = new Student("Lusta Bela", LocalDate.parse("2022-01-01"), Student.ChargeType.PAYING);
        repoSt.save(student);
        repoSt.updateStudentWithTraining(student.getId(), training3.getId());

        Trainer trainerCopy = repoTr.findTrainerByIdWithTrainingsAndStudents_graph(trainer.getId());
        Assertions.assertThat(trainerCopy.getTrainings()).hasSize(3);
        Assertions.assertThat(trainerCopy.getTrainings().get(0).getStudents()).hasSize(2);
        Assertions.assertThat(trainerCopy.getTrainings().get(1).getStudents()).hasSize(1);
        Assertions.assertThat(trainerCopy.getTrainings().get(2).getStudents()).hasSize(3);
    }

}
