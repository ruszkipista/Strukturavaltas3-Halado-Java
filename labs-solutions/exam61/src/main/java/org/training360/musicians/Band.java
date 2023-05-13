package org.training360.musicians;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "bands")
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ElementCollection(targetClass=Album.class)
    @CollectionTable(name="albums", joinColumns=@JoinColumn(name="band_id")) 
    @Column(name="album")
    private Set<Album> albums = new HashSet<>();

    @OneToMany(mappedBy = "band", cascade = CascadeType.PERSIST)
    private Set<Musician> musicians = new HashSet<>();
    
    public Band() {
    }

    public Band(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
    }
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBandName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Genre getGenre() {
        return genre;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void addAlbum(Album album){
        this.albums.add(album);
    }

    public Set<Album> getDiscography(){
        return this.albums;
    }

    public Set<Musician> getMusicians() {
        return this.musicians;
    }

    public void addMusician(Musician musician) {
        this.musicians.add(musician);
        musician.setBand(this);
    }
    
}
