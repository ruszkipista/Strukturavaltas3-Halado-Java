package org.training360.students;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    StudentRepository repo;

    @InjectMocks
    StudentService service;

    String studentName = "joskapista";
    String subjectTitle = "java";
    Student student;

    @BeforeEach
    void init(){
        student = new Student(studentName, LocalDate.parse("1970-01-01"));
    }

    @Test
    void testSaveNewStudent_SameReturned(){
        Mockito.when(repo.saveNewStudent(any())).thenReturn(student);

        service.saveNewStudent(student);

        Mockito.verify(repo, times(1)).saveNewStudent(argThat(s->s.getName().equals(studentName)));
    }

    @Test
    void testcalculateStudentAverageBySubject_CalculationCorrect(){
        student.addGradeWithSubject(subjectTitle, 80);
        student.addGradeWithSubject(subjectTitle, 90);
        student.addGradeWithSubject("SQL", 10);
        student.addGradeWithSubject("SQL", 20);

        Mockito.when(repo.findStudentByName(studentName))
                .thenReturn(Optional.of(student));

        double subjectAvg = service.calculateStudentAverageBySubject(student.getName(), subjectTitle);

        assertEquals(85.0, subjectAvg, 0.000001);
    }

    @Test
    void testcalculateStudentAverageBySubject_SubjectNotStudied(){
        student.addGradeWithSubject(subjectTitle, 80);
        student.addGradeWithSubject(subjectTitle, 90);
        student.addGradeWithSubject("SQL", 10);
        student.addGradeWithSubject("SQL", 20);

        Mockito.when(repo.findStudentByName(studentName))
                .thenReturn(Optional.of(student));

        double subjectAvg = service.calculateStudentAverageBySubject(student.getName(), "notAsubject");

        assertEquals(0.0, subjectAvg, 0.000001);
        Mockito.verify(repo, times(1)).findStudentByName(studentName);
    }

    @Test
    void testcalculateStudentAverageBySubject_NoSuchStudentWithName(){
        Mockito.when(repo.findStudentByName(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, 
            ()->service.calculateStudentAverageBySubject("", ""));

        Mockito.verify(repo, times(1)).findStudentByName(any());
    }

    @Test
    void testFindStudentsWithMoreGradesThan2_2StudentsFound(){
        List<Student> students = new ArrayList<>();
        student.addGradeWithSubject(subjectTitle, 70);
        student.addGradeWithSubject(subjectTitle, 80);
        student.addGradeWithSubject("SQL", 10);
        students.add(student);

        student = new Student("okostoni", LocalDate.parse("1975-01-01"));
        student.addGradeWithSubject(subjectTitle, 60);
        student.addGradeWithSubject(subjectTitle, 90);
        students.add(student);

        student = new Student("szorgosmarcsi", LocalDate.parse("1980-01-01"));
        student.addGradeWithSubject(subjectTitle, 60);
        student.addGradeWithSubject(subjectTitle, 90);
        student.addGradeWithSubject("SQL", 50);
        student.addGradeWithSubject("SQL", 40);
        students.add(student);

        Mockito.when(repo.findAllStudents()).thenReturn(students);

        List<Student> filteredStudents = service.findStudentsWithMoreGradesThan(2);
        Assertions.assertThat(filteredStudents)
            .hasSize(2)
            .extracting(Student::getName)
            .containsExactlyInAnyOrder("szorgosmarcsi", "joskapista");

        Mockito.verify(repo, times(1)).findAllStudents();
    }
}
