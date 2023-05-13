package org.training360.students;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {

    private String name;

    private LocalDate dateOfBirth;

    private Map<String, List<Integer>> subjectAndGrades = new HashMap<>();

    public Student(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public void addGradeWithSubject(String subject, int grade) {
        if (!subjectAndGrades.containsKey(subject)) {
            subjectAndGrades.put(subject, new ArrayList<>());
        }
        subjectAndGrades.get(subject).add(grade);
    }

    public long countNumberOfGrades() {
        return subjectAndGrades.entrySet().stream()
                .flatMap(e -> e.getValue().stream())
                .count();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Map<String, List<Integer>> getSubjectAndGrades() {
        return subjectAndGrades;
    }

    public void setSubjectAndGrades(Map<String, List<Integer>> subjectAndGrades) {
        this.subjectAndGrades = subjectAndGrades;
    }

    public List<Integer> getGradesOfSubject(String subject) {
        return subjectAndGrades.get(subject);
    }
}
