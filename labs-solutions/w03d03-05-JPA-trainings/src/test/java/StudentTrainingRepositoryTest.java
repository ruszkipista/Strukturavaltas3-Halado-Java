import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTrainingRepositoryTest {
    EntityManagerFactory entityManagerFactory;
    StudentRepository repoSt;
    TrainingRepository repoTg;

    @BeforeEach
    void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        repoSt = new StudentRepository(entityManagerFactory);
        repoTg = new TrainingRepository(entityManagerFactory);
    }

    @AfterEach
    void close(){
        entityManagerFactory.close();
    }

    @Test
    void save1Student_foundById(){
        Student student = new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE);
        repoSt.save(student);

        Student studentCopy = repoSt.findStudentById(student.getId());
        assertEquals(student.getName(), studentCopy.getName());
    }

    @Test
    void getListOfAll_itIsEmpty(){
        List<Student> students = repoSt.listAll();
        assertEquals(List.of(), students);
    }

    @Test
    void addTrainingTo2Students(){
        Training training = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-03-31"));
        repoTg.save(training);
        assertEquals(0, training.getStudents().size());
        Student student = new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE);
        repoSt.save(student);
        assertEquals(0, student.getTrainings().size());

        repoSt.updateStudentWithTraining(student.getId(), training.getId());
        
        Training trainingCopy = repoTg.findTrainingByIdWithStudents(training.getId());
        assertEquals(1, trainingCopy.getStudents().size());
        Student studentCopy = repoSt.findStudentByIdWithTrainings(student.getId());
        assertEquals(1, studentCopy.getTrainings().size());
        
        student = new Student("Szorgos Juli", LocalDate.parse("2021-01-01"), Student.ChargeType.FREE);
        repoSt.save(student);

        repoSt.updateStudentWithTraining(student.getId(), training.getId());

        trainingCopy = repoTg.findTrainingByIdWithStudents(training.getId());
        assertEquals(2, trainingCopy.getStudents().size());
        studentCopy = repoSt.findStudentByIdWithTrainings(student.getId());
        assertEquals(1, studentCopy.getTrainings().size());
    }

    @Test
    void add1StudentTo2Trainings_collectionCountsCorrect(){
        Student student = new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE);
        repoSt.save(student);
        Training training = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-03-31"));
        repoTg.save(training);

        repoTg.updateTrainingWithStudent(training.getId(), student.getId());

        Training trainingCopy = repoTg.findTrainingByIdWithStudents(training.getId());
        assertEquals(1, trainingCopy.getStudents().size());
        Student studentCopy = repoSt.findStudentByIdWithTrainings(student.getId());
        assertEquals(1, studentCopy.getTrainings().size());

        training = new Training("ike bana", LocalDate.parse("2023-04-01"), LocalDate.parse("2023-06-30"));
        repoTg.save(training);

        repoTg.updateTrainingWithStudent(training.getId(), student.getId());

        studentCopy = repoSt.findStudentByIdWithTrainings(student.getId());
        assertEquals(2, studentCopy.getTrainings().size());
        trainingCopy = repoTg.findTrainingByIdWithStudents(training.getId());
        assertEquals(1, trainingCopy.getStudents().size());
    }

    @Test
    void add2TrainingsToStudent(){
        Student student = new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE);
        repoSt.save(student);
        Training training = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-03-31"));
        repoTg.save(training);

        repoSt.updateStudentWithTraining(student.getId(), training.getId());

        Training trainingCopy = repoTg.findTrainingByIdWithStudents(training.getId());
        assertEquals(1, trainingCopy.getStudents().size());
        Student studentCopy = repoSt.findStudentByIdWithTrainings(student.getId());
        assertEquals(1, studentCopy.getTrainings().size());

        training = new Training("ike bana", LocalDate.parse("2023-04-01"), LocalDate.parse("2023-06-30"));
        repoTg.save(training);

        repoSt.updateStudentWithTraining(student.getId(), training.getId());
        
        studentCopy = repoSt.findStudentByIdWithTrainings(student.getId());
        assertEquals(2, studentCopy.getTrainings().size());
        trainingCopy = repoTg.findTrainingByIdWithStudents(training.getId());
        assertEquals(1, trainingCopy.getStudents().size());
    }

    @Test
    void add2StudentsToTraining(){
        Training training = new Training("feng shui", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-03-31"));
        repoTg.save(training);
        assertEquals(0, training.getStudents().size());
        Student student = new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE);
        repoSt.save(student);
        assertEquals(0, student.getTrainings().size());

        repoTg.updateTrainingWithStudent(training.getId(), student.getId());
        
        Training trainingCopy = repoTg.findTrainingByIdWithStudents(training.getId());
        assertEquals(1, trainingCopy.getStudents().size());
        Student studentCopy = repoSt.findStudentByIdWithTrainings(student.getId());
        assertEquals(1, studentCopy.getTrainings().size());
        
        student = new Student("Szorgos Juli", LocalDate.parse("2021-01-01"), Student.ChargeType.FREE);
        repoSt.save(student);

        repoTg.updateTrainingWithStudent(training.getId(), student.getId());

        trainingCopy = repoTg.findTrainingByIdWithStudents(training.getId());
        assertEquals(2, trainingCopy.getStudents().size());
        studentCopy = repoSt.findStudentByIdWithTrainings(student.getId());
        assertEquals(1, studentCopy.getTrainings().size());
    }

    @Test
    void findStudentsByChargeType_found2(){
        repoSt.save(new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE));
        repoSt.save(new Student("Szorgos Juli", LocalDate.parse("2021-01-01"), Student.ChargeType.FREE));
        repoSt.save(new Student("Lusta Bela", LocalDate.parse("2022-01-01"), Student.ChargeType.PAYING));

        List<Student> students = repoSt.findStudentsByChargeType(Student.ChargeType.FREE);
        Assertions.assertThat(students)
            .hasSize(2)
            .extracting(Student::getName)
            .containsExactlyInAnyOrder("Szorgos Juli","Okos Toni");
    }

    @Test
    void findStudentsByName_NotFound(){
        Optional<Student> studentCopy = repoSt.findByName("Lusta Marci");
        assertEquals(Optional.empty(), studentCopy);
    }

    @Test
    void findStudentsByName_found1(){
        repoSt.save(new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE));
        repoSt.save(new Student("Szorgos Juli", LocalDate.parse("2021-01-01"), Student.ChargeType.FREE));

        Optional<Student> studentCopy = repoSt.findByName("Szorgos Juli");
        assertEquals("Szorgos Juli", studentCopy.get().getName());
    }

    @Test
    void save3ThenUpdateNameById_NameChanged(){
        repoSt.save(new Student("Szorgos Juli", LocalDate.parse("2021-01-01"), Student.ChargeType.FREE));
        repoSt.save(new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE));
        Student student = new Student("Lusta Bela", LocalDate.parse("2022-01-01"), Student.ChargeType.PAYING);
        repoSt.save(student);

        String newName = "Lusta Marci";
        repoSt.updateNameById(student.getId(), newName);
        Student studentCopy = repoSt.findStudentById(student.getId());

        assertEquals(newName, studentCopy.getName());
    }

    @Test
    void save3ThenRemove2nd_listAll2(){
        repoSt.save(new Student("Szorgos Juli", LocalDate.parse("2021-01-01"), Student.ChargeType.FREE));
        Student student = new Student("Lusta Bela", LocalDate.parse("2022-01-01"), Student.ChargeType.PAYING);
        repoSt.save(student);
        repoSt.save(new Student("Okos Toni", LocalDate.parse("2020-01-01"), Student.ChargeType.FREE));

        repoSt.removeStudentById(student.getId());

        Assertions.assertThat(repoSt.listAll())
            .hasSize(2)
            .extracting(Student::getName)
            .containsExactlyInAnyOrder("Szorgos Juli","Okos Toni");
    }
}
