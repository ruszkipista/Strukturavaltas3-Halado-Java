package user;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate registrationDate;

    @OneToMany(mappedBy = "user")
    private Set<Video> videos = new HashSet<>();

    @OneToMany
    private Set<Comment> comments = new HashSet<>();

    public User() {}

    public User(String name, LocalDate registrationDate) {
        this.name = name;
        this.registrationDate = registrationDate;
    }

    public void addVideo(Video video){
        video.setUser(this);
        this.videos.add(video);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Video> getVideos() {
        return this.videos;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
