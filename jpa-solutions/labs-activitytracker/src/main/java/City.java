import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cities")
public class City {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int population;

    @ManyToOne
    private Area area;

    public City() {
    }

    public City(String name, int population, Area area) {
        this.name = name;
        this.population = population;
        this.area = area;
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

    public int getPopulation() {
        return this.population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof City)) {
            return false;
        }
        City city = (City) o;
        return Objects.equals(name, city.name) && Objects.equals(area, city.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area);
    }

}
