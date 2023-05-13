package videos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "name")
    private String name;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Video> videos = new HashSet<>();

    public User() {
    }

    private User(Long id, String name, LocalDate registrationDate, UserStatus userStatus) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.userStatus = userStatus;
    }

    public User(Long id, String name, LocalDate registrationDate) {
        this(id, name, registrationDate, UserStatus.BEGINNER);
    }

    public User(String name, LocalDate registrationDate) {
        this(null, name, registrationDate, UserStatus.BEGINNER);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UserStatus getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

}
