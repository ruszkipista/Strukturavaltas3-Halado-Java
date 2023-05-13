package movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieDto {
    private Long id;
    private String title;
    private int lengthInMins;
    private double averageRating;
}
