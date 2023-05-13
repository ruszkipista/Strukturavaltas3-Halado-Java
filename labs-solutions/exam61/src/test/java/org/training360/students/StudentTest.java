package org.training360.students;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTest {
    Student student;

    @BeforeEach
    void init(){
        student = new Student("joskapista", LocalDate.parse("1970-01-01"));
    }

    @Test
    void testAddGradeToNewSubject_SubjectAddedAndGradeAdded(){
        student.addGradeWithSubject("java", 89);

        assertEquals(1, student.getGradesOfSubject("java").size());
        assertEquals(89, student.getGradesOfSubject("java").get(0));
    }

    @Test
    void testAddGradeToExistingSubject_GradeAdded(){
        student.addGradeWithSubject("java", 89);
        student.addGradeWithSubject("java", 90);

        assertEquals(2, student.getGradesOfSubject("java").size());
        assertEquals(90, student.getGradesOfSubject("java").get(1));
    }

    @Test
    void testcountNumberOfGrades_empty(){
        long count = student.countNumberOfGrades();

        assertEquals(0L, count);
    }

    @Test
    void testcountNumberOfGrades_3found(){
        student.addGradeWithSubject("SQL", 88);
        student.addGradeWithSubject("java", 89);
        student.addGradeWithSubject("java", 90);

        long count = student.countNumberOfGrades();

        assertEquals(3L, count);
    }
}
