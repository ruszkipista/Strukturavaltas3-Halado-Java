import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    long userId = 3L;
    String username = "user42";
    String passwordGood = "1234";
    String passwordBad = "1235";
    String passwordHash = "202";
    User userInDB;

    @Mock
    UserRepository repo;

    @InjectMocks
    UserService service;

    @BeforeEach
    void init(){
        userInDB = new User(userId, username, passwordHash, User.UserRole.USER);
    }

    @Test
    void testUserSave_savedPasswordMatches() {
        when(repo.save(any())).thenReturn(userInDB);

        User userSaved = service.saveUser(username, passwordGood, User.UserRole.USER);
        
        assertEquals(userId, userSaved.getId());
        assertEquals(passwordHash, userSaved.getPasswordHash());
        verify(repo).save(argThat(u->u.getPasswordHash().equals(userSaved.getPasswordHash())));
    }

    @Test
    void testLogIn_success() {
        when(repo.findByUsername(username))
                .thenReturn(Optional.of(userInDB));

        assertTrue(service.logInUser(username, "1234"));
        verify(repo).findByUsername(username);
    }

    @Test
    void testLogIn_notSuccessful() {
        when(repo.findByUsername(username))
                .thenReturn(Optional.of(userInDB));

        assertFalse(service.logInUser(username, "1235"));
        verify(repo).findByUsername(username);
    }
}
