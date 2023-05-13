import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "areas")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "areas")
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "area", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapKey(name = "name")
    private Map<String, City> cities = new HashMap<>();

    public Area() {
    }

    public Area(String name) {
        this.name = name;
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

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public Map<String,City> getCities() {
        return this.cities;
    }

    public void addCity(City city) {
        this.cities.put(city.getName(), city);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Area)) {
            return false;
        }
        Area area = (Area) o;
        return Objects.equals(name, area.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
