import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "trainings")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    private Trainer trainer;

    @ManyToMany
    @JoinTable(name = "traing_studnt", 
    joinColumns = @JoinColumn(name = "training_id"), 
    inverseJoinColumns = @JoinColumn(name="student_id"))
    private Set<Student> students = new HashSet<>();

    public Training() {
    }

    public Training(Long id, String title, LocalDate startDate, LocalDate endDate, Trainer trainer) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.trainer = trainer;
    }

    public Training(String title, LocalDate startDate, LocalDate endDate, Trainer trainer) {
        this(null, title, startDate, endDate, trainer);
    }

    public Training(String title, LocalDate startDate, LocalDate endDate) {
        this(null, title, startDate, endDate, null);
    }

    public Trainer getTrainer() {
        return this.trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
        trainer.getTrainings().add(this);
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.getTrainings().add(this);
    }

    public void addStudents(List<Student> students) {
        students.stream().forEach(tr->addStudent(tr));
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "{" +
            "id:" + getId() +
            ", title:" + getTitle() +
            ", students:" + getStudents() +
            "}";
    }

}