import java.time.LocalDate;

public class Movie {
    private Long id;
    private String title;
    private LocalDate productionDate;
    private int lengthInMinutes;
    private MovieGenre genre;

    public Movie(Long id, String title, LocalDate productionDate, int lengthInMinutes, MovieGenre genre) {
        this.id = id;
        this.title = title;
        this.productionDate = productionDate;
        this.lengthInMinutes = lengthInMinutes;
        this.genre = genre;
    }

    public Movie(String title, LocalDate productionDate, int lengthInMinutes, MovieGenre genre) {
        this(null, title, productionDate, lengthInMinutes, genre);
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDate getProductionDate() {
        return this.productionDate;
    }

    public int getLengthInMinutes() {
        return this.lengthInMinutes;
    }

    public MovieGenre getGenre() {
        return this.genre;
    }

}
