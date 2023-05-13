package flights.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "routes",
       uniqueConstraints = { @UniqueConstraint(name = "UniqueAirplanePerDay", 
                                        columnNames = { "airplane_id", "date_of_flight" }) })
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    @JsonBackReference
    private Airplane airplane;

    @Column(name = "departure_city")
    private String departureCity;
    @Column(name = "arrival_city")
    private String arrivalCity;
    @Column(name = "date_of_flight")
    private LocalDate dateOfFlight;

    public Route(Airplane airplane, String departureCity, String arrivalCity, LocalDate dateOfFlight) {
        this(null, airplane, departureCity, arrivalCity, dateOfFlight);
    }

}