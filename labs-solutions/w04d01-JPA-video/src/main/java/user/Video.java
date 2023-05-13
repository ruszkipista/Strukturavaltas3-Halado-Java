package user;

import java.time.LocalTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalTime length;

    @ManyToOne
    private User user;

    @ManyToMany
    private Set<Tag> tags;

    public Video() {
    }

    public Video(String title, LocalTime length) {
        this.title = title;
        this.length = length;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalTime getLength() {
        return length;
    }

    public void setLength(LocalTime length) {
        this.length = length;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
