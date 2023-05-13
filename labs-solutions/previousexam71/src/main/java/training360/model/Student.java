package training360.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name="school_age_status")
    private SchoolAgeStatus schoolAgeStatus;

    public Student(String name, LocalDate dateOfBirth, SchoolAgeStatus schoolAgeStatus) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.schoolAgeStatus = schoolAgeStatus;
    }

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonBackReference
    private School school;

}