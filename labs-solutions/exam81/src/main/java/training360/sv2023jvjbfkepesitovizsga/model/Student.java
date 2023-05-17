package training360.sv2023jvjbfkepesitovizsga.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Embedded
    @AttributeOverride(name="schoolName",column=@Column(name="school_name"))
    private School school;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    private List<Test> tests = new ArrayList<>();

    public Student(String name, String schoolName, String City){
        this.name = name;
        this.school = new School(schoolName, City);
    }

    public void addTest(Test test) {
        tests.add(test);
        test.setStudent(this);
    }

}
