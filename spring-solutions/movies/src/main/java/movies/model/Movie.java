package movies.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private int lengthInMins;
    private List<Rating> ratings;
    
    public Movie(String title, int lengthInMins, List<Rating> ratings) {
        this(null,title, lengthInMins, ratings);
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }

    public double getAverageRating() {
        return this.ratings.stream()
            .mapToInt(Rating::getValue)
            .average()
            .orElse(0d);
    }

    @Override
    public String toString() {
        return "Movie{id=" + id + ", title=" + title + ", lengthInMins=" + lengthInMins
             + ", averageRating=" + getAverageRating() + " from " +  ratings.size() +" ratings}";
    }   
}
