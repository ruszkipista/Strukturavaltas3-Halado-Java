import java.util.Optional;

public class UserService {
    
    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User saveUser(String username, String passwordNotEncoded, User.UserRole role){
        User user = new User(username, hashCode(passwordNotEncoded), role);
        return repo.save(user);
    }

    public String hashCode(String passwordNotEncoded){
        return Integer.toString(passwordNotEncoded.chars().sum());
    }

    public User findUserById(long id){
        return repo.findById(id);
    }

    public boolean logInUser(String username, String passwordNotEncoded){
        Optional<User> result = this.repo.findByUsername(username);
        if (!result.isPresent())
            return false;
        return hashCode(passwordNotEncoded).equals(result.get().getPasswordHash());
    }
}
