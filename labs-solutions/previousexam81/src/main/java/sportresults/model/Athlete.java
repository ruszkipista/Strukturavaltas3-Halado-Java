package sportresults.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="athletes")
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToMany(mappedBy = "athlete")
    @JsonManagedReference
    private List<Result> results = new ArrayList<>();

    public Athlete(String name, Sex sex) {
        this.name = name;
        this.sex = sex;
    }

    public void addResult(Result result) {
        results.add(result);
        result.setAthlete(this);
    }

}
