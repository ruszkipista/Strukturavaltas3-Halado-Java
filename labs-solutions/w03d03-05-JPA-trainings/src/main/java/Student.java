import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    public enum ChargeType {FREE, PAYING}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String name;

    @Column(name="registration_date")
    private LocalDate registrationDate;

    @Column(name="charge_type")
    @Enumerated(EnumType.STRING)
    ChargeType chargeType;

    @ManyToMany(mappedBy = "students")
    private Set<Training> trainings = new HashSet<>();

    public Student() {}

    public Student(String name, LocalDate registrationDate, ChargeType chargeType) {
        this.name = name;
        this.registrationDate = registrationDate;
        this.chargeType = chargeType;
    }

    public void addTraining(Training training) {
        this.trainings.add(training);
        training.getStudents().add(this);
    }

    public void addTrainings(Set<Training> trainings) {
        trainings.stream().forEach(st->addTraining(st));
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Training> getTrainings() {
        return this.trainings;
    }


    @Override
    public String toString() {
        return "{" +
            "id=" + getId() +
            ", name=" + getName() +
            ", registrationDate=" + getRegistrationDate() +
            "}";
    }

}
