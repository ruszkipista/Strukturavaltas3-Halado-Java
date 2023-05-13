package jpa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nests")
@Inheritance(strategy = InheritanceType.JOINED)
public class Nest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nest_id")
    private Long id;

    @Column(name = "number_of_eggs")
    private int numberOfEggs;

    @OneToMany(mappedBy = "nest")
    private Set<Bird> birds = new HashSet<>();

    public Nest() {
    }

    public Nest(int numberOfEggs) {
        this.numberOfEggs = numberOfEggs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfEggs() {
        return numberOfEggs;
    }

    public void setNumberOfEggs(int numberOfEggs) {
        this.numberOfEggs = numberOfEggs;
    }

    public Set<Bird> getBirds() {
        return birds;
    }
}
