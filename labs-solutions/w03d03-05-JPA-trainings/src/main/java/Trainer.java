import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "trainers")
@NamedEntityGraph(name = "trainer-entity-graph-with-trainings",
    attributeNodes = {
        @NamedAttributeNode(
            value = "trainings",
            subgraph = "training-entity-graph-with-students"
        )
    },
    subgraphs = {
        @NamedSubgraph(
            name = "training-entity-graph-with-students",
                attributeNodes = { @NamedAttributeNode("students") }
        )
    }
)
public class Trainer {
    public enum ProfessionLevel { JUNIOR, MEDIOR, SENIOR };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProfessionLevel status;

    @OneToMany(mappedBy = "trainer")
    private List<Training> trainings = new ArrayList<>();

    public Trainer() {
    }

    public Trainer(Long id, String name, ProfessionLevel status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Trainer(String name, ProfessionLevel status) {
        this(null, name, status);
    }

    public List<Training> getTrainings() {
        return this.trainings;
    }

    public void addTraining(Training training) {
        training.setTrainer(this);
        this.trainings.add(training);
    }

    public void addTrainings(List<Training> trainings) {
        trainings.stream().forEach(tr->addTraining(tr));
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

    public ProfessionLevel getStatus() {
        return this.status;
    }

    public void setStatus(ProfessionLevel status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
            "id:" + getId() +
            ", name:" + getName() +
            ", trainings:" + getTrainings() +
            "}";
    }

}