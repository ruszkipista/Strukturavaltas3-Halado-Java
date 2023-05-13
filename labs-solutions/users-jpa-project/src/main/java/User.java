import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    enum UserRole {ADMIN, USER};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique=true)
    private String username;
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(Long id, String username, String passwordHash, UserRole role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(String username, String passwordHash, UserRole role) {
        this(null, username, passwordHash, role);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{" +
            "id=" + getId() + 
            ", username=" + getUsername() + 
            ", passwordHash=" + getPasswordHash() + 
            ", role=" + getRole() + 
            "}";
    }

}