package user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class UserDao_HandlerTest {

    UserDao_Handler userDao;

    @BeforeEach
    void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        PersistenceContextHandler persistenceContextHandler = new PersistenceContextHandler(entityManagerFactory);
        userDao = new UserDao_Handler(persistenceContextHandler);
    }

    @Test
    void testSaveUser() {
        User user = new User("John", LocalDate.of(2021,11,11));

        userDao.saveUser(user);

        User other = userDao.findUser(user.getId());

        assertEquals("John", other.getName());
        assertEquals(LocalDate.of(2021, 11, 11), other.getRegistrationDate());
    }

    @Test
    void getUser(){
        User user = new User("John", LocalDate.of(2021,11,11));
        userDao.saveUser(user);

        User userCopy = userDao.getUser(user);

        assertNotNull(userCopy.getId());
        assertEquals("John", userCopy.getName());
        assertEquals(LocalDate.of(2021, 11, 11), userCopy.getRegistrationDate());        
    }

    @Test
    void testFindUserWithVideos() {
        User user = new User("Jack", LocalDate.of(2022,11,11));
        userDao.saveUser(user);
        user = new User("John", LocalDate.of(2021,11,11));
        userDao.saveUser(user);
        Video video = new Video("My first video", LocalTime.of(1, 23, 31));

        userDao.addVideoToUser(user.getId(), video);

        User userCopy = userDao.findUserByIdWithVideos(user.getId());

        assertEquals("John", userCopy.getName());
        assertEquals(LocalDate.of(2021, 11, 11), userCopy.getRegistrationDate());
        assertEquals(1, userCopy.getVideos().size());
    }
}