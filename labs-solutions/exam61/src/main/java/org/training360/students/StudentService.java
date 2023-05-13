package org.training360.students;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class StudentService {

    private StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student saveNewStudent(Student student) {
        return repository.saveNewStudent(student);
    }

    public double calculateStudentAverageBySubject(String studentName, String subject) {
        Optional<Student> actual = repository.findStudentByName(studentName);
        if (actual.isEmpty()) {
            throw new IllegalArgumentException("Cannot find student with this name: " + studentName);
        }
        Student student = actual.get();
        List<Integer> gradesOfSubject = student.getGradesOfSubject(subject);
        return gradesOfSubject == null ? 0.0 : calculateAverage(gradesOfSubject);
    }

    private double calculateAverage(List<Integer> grades) {
        OptionalDouble result = grades.stream()
                .mapToDouble(g -> g)
                .average();
        return result.isPresent() ? result.getAsDouble() : 0.0;
    }

    public List<Student> findStudentsWithMoreGradesThan(int count) {
        List<Student> students = repository.findAllStudents();
        return students.stream()
                .filter(s -> s.countNumberOfGrades() > count)
                .toList();
    }
}
