package movies.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreateCommand {
    private String title;
    private int lengthInMins;
    private List<RatingDto> ratings;
}
