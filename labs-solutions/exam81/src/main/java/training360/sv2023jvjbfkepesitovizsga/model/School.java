package training360.sv2023jvjbfkepesitovizsga.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {
    private String schoolName;
    private String city;
}
