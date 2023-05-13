package org.training360.musicians;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Album {  
    private String title;
    
    @Column(name = "release_date")
    private LocalDate releaseDate;
    
    public Album() {
    }

    public Album(String title, LocalDate releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
}
