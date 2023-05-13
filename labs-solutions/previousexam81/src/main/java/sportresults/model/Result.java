package sportresults.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String place;

    @Column(name = "result_date")
    private LocalDate resultDate;

    @Enumerated(EnumType.STRING)
    private SportType sportType;

    private double measure;

    @Column(name = "measure_unit")
    private char measureUnit;

    @ManyToOne
    @JoinColumn(name = "athlete_id")
    @JsonBackReference
    private Athlete athlete;


    public Result(String place, LocalDate resultDate, SportType sportType, double measure) {
        this.place = place;
        this.resultDate = resultDate;
        this.sportType = sportType;
        this.measure = measure;
        this.measureUnit = sportType.getMeasureUnit();
    }
}
