package training360.guinessapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class WorldRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double value;

    private String unitOfMeasure;

    private LocalDate dateOfRecord;

    @ManyToOne
    private Recorder recorder;
}
