import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRepositoryTest {
    EntityManagerFactory entityManagerFactory;
    UserRepository repo;

    @BeforeEach
    void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        repo = new UserRepository(entityManagerFactory);
    }

    @AfterEach
    void close(){
        entityManagerFactory.close();
    }

    @Test
    void testListAll_Empty(){
        List<User> users = repo.listAll();
        assertEquals(List.of(), users);
    }

    @Test
    void testSave1_findById(){
        User user = repo.save(new User("admin","123567", User.UserRole.ADMIN));

        Assertions.assertThat(List.of(repo.findById(user.getId())))
            .extracting(User::getUsername, User::getRole)
            .contains(Assertions.tuple("admin", User.UserRole.ADMIN));
    }

    @Test
    void save2SameUsername_fails(){
        repo.save(new User("admin","123567", User.UserRole.ADMIN));
        repo.save(new User("user1","pa55word", User.UserRole.USER));
        assertThrows(PersistenceException.class, () -> repo.save(new User("user1","init123", User.UserRole.USER)));
    }

    @Test
    void testSave3_listThemAll(){
        repo.save(new User("admin","123567", User.UserRole.ADMIN));
        repo.save(new User("user1","pa55word", User.UserRole.USER));
        repo.save(new User("user2","init123", User.UserRole.USER));

        Assertions.assertThat(repo.listAll())
            .hasSize(3)
            .extracting(User::getUsername)
            .containsExactly("admin", "user1", "user2");
    }

    @Test
    void testUpdatePasswordById(){
        repo.save(new User("admin","123567", User.UserRole.ADMIN));
        User userW = repo.save(new User("user1","pa55word", User.UserRole.USER));
        repo.save(new User("user2","init123", User.UserRole.USER));

        String newPassword = "newPa55";
        repo.updatePasswordById(userW.getId(), newPassword);
        User userR = repo.findById(userW.getId());

        assertEquals(newPassword, userR.getPasswordHash());
    }

    @Test
    void testUpdatePasswordByUsername(){
        repo.save(new User("admin","123567", User.UserRole.ADMIN));
        User userW = repo.save(new User("user1","pa55word", User.UserRole.USER));
        repo.save(new User("user2","init123", User.UserRole.USER));

        String newPasswordHash = "newPa55";
        repo.updatePasswordByUsername("user1", newPasswordHash);
        User userR = repo.findById(userW.getId());

        assertEquals(newPasswordHash, userR.getPasswordHash());
    }

    @Test
    void save3ThenRemove2nd_listAll2(){
        repo.save(new User("admin","123567", User.UserRole.ADMIN));
        repo.save(new User("user1","pa55word", User.UserRole.USER));
        User userW = repo.save(new User("user2","init123", User.UserRole.USER));

        repo.removeById(userW.getId());

        Assertions.assertThat(repo.listAll())
            .hasSize(2)
            .extracting(User::getUsername)
            .containsExactly("admin", "user1");
    }

}
