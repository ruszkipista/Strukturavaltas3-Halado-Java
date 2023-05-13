package training360.model;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="schools")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Embedded
    @AttributeOverride(name="postalCode", column=@Column(name="postal_code"))
    @AttributeOverride(name="city", column=@Column(name="city"))
    @AttributeOverride(name="street", column=@Column(name="street"))
    @AttributeOverride(name="houseNumber", column=@Column(name="house_nr"))
    private Address address;

    @OneToMany(mappedBy = "school")
    @JsonManagedReference
    private List<Student> students = new ArrayList<>();

    public School(String schoolName, Address address) {
        this.name = schoolName;
        this.address = address;
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setSchool(this);
    }

}
