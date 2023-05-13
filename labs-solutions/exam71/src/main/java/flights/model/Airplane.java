package flights.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "airplanes")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_airline")
    private String ownerAirline;

    @Enumerated(EnumType.STRING)
    @Column(name = "airplane_type")
    private AirplaneType airplaneType;

    @OneToMany(mappedBy = "airplane")
    @JsonManagedReference
    private List<Route> routes = new ArrayList<>();
    
    public Airplane(Long id, String ownerAirline, AirplaneType airplaneType) {
        this.id = id;
        this.ownerAirline = ownerAirline;
        this.airplaneType = airplaneType;
    }

    public Airplane(String ownerAirline, AirplaneType airplaneType) {
        this(null, ownerAirline, airplaneType);
    }

    public void addRoute(Route route) {
        routes.add(route);
        route.setAirplane(this);
    }

    public void removeRoute(Route route) {
        this.routes.remove(route);
        route.setAirplane(null);
    }

}
