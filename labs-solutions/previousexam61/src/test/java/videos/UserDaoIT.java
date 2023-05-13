package videos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

class UserDaoIT {
    EntityManagerFactory entityManagerFactory;
    UserDao userDao;
    VideoDao videoDao;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        userDao = new UserDao(entityManagerFactory);
        videoDao = new VideoDao(entityManagerFactory);
    }

    @AfterEach
    void close() {
        entityManagerFactory.close();
    }

    @Test
    void createUser() {
        User user = new User("ruszkipista", LocalDate.parse("2023-03-04"));

        assertNull(user.getId());
        assertEquals(UserStatus.BEGINNER, user.getUserStatus());
    }

    @Test
    void testSaveUser() {
        User user = new User("ruszkipista", LocalDate.parse("2023-03-04"));

        userDao.saveUser(user);
        assertNotNull(user.getId());

        User userCopy = userDao.findUserById(user.getId());
        Assertions.assertThat(List.of(userCopy))
                .extracting(User::getName, User::getRegistrationDate, User::getUserStatus)
                .containsOnly(Assertions.tuple("ruszkipista", LocalDate.parse("2023-03-04"), UserStatus.BEGINNER));
    }

    @Test
    void testAddVideoToUser() {
        User user = new User("dummy", LocalDate.parse("2023-01-01"));
        userDao.saveUser(user);
        user = new User("ruszkipista", LocalDate.parse("2023-03-04"));
        userDao.saveUser(user);

        userDao.updateUserWithVideo(user.getId(), new Video("my life in 2 mins"));
        userDao.updateUserWithVideo(user.getId(), new Video("my happy moments"));

        User userCopy = userDao.findUserByIdWithVideos(user.getId());

        Assertions.assertThat(userCopy.getVideos().stream().toList())
                .hasSize(2)
                .extracting(Video::getTitle)
                .containsExactlyInAnyOrder("my life in 2 mins", "my happy moments");
    }

    @Test
    void testChangeUserStatus() {
        User user = new User("ruszkipista", LocalDate.parse("2023-03-04"));
        userDao.saveUser(user);

        userDao.updateUserStatus(user.getId(), UserStatus.ADVANCED);

        User userCopy = userDao.findUserById(user.getId());
        assertEquals(user.getId(), userCopy.getId());
        assertEquals(UserStatus.ADVANCED, userCopy.getUserStatus());
    }

    @Test
    void findUsersWithMoreThan2Videos_2UsersFound() {
        User user = new User("hasmore3", LocalDate.parse("2023-01-01"));
        userDao.saveUser(user);
        userDao.updateUserWithVideo(user.getId(), new Video("video1"));
        userDao.updateUserWithVideo(user.getId(), new Video("video2"));
        userDao.updateUserWithVideo(user.getId(), new Video("video3"));

        user = new User("hasless0", LocalDate.parse("2023-02-01"));
        userDao.saveUser(user);

        user = new User("hasless2", LocalDate.parse("2023-02-01"));
        userDao.saveUser(user);
        userDao.updateUserWithVideo(user.getId(), new Video("video1"));
        userDao.updateUserWithVideo(user.getId(), new Video("video2"));

        user = new User("hasmore4", LocalDate.parse("2023-03-01"));
        userDao.saveUser(user);
        userDao.updateUserWithVideo(user.getId(), new Video("video1"));
        userDao.updateUserWithVideo(user.getId(), new Video("video2"));
        userDao.updateUserWithVideo(user.getId(), new Video("video3"));
        userDao.updateUserWithVideo(user.getId(), new Video("video4"));

        List<User> users = userDao.findUsersWithMoreVideosThan(2);

        Assertions.assertThat(users)
                .hasSize(2)
                .extracting(User::getName)
                .containsExactlyInAnyOrder("hasmore3", "hasmore4");
    }

    @Test
    void removeUserById_UserAndVideosDeleted() {
        User userToBeKept = new User("userKeep", LocalDate.parse("2023-02-01"));
        userDao.saveUser(userToBeKept);
        userDao.updateUserWithVideo(userToBeKept.getId(), new Video("videoKeep1"));
        userDao.updateUserWithVideo(userToBeKept.getId(), new Video("videoKeep2"));

        final User userToBeRemoved = new User("userRemove", LocalDate.parse("2023-01-01"));
        userDao.saveUser(userToBeRemoved);
        userDao.updateUserWithVideo(userToBeRemoved.getId(), new Video("videoR1"));
        userDao.updateUserWithVideo(userToBeRemoved.getId(), new Video("videoR2"));
        userDao.updateUserWithVideo(userToBeRemoved.getId(), new Video("videoR3"));

        userDao.removeUserById(userToBeRemoved.getId());

        assertThrows(NoResultException.class, () -> userDao.findUserByIdWithVideos(userToBeRemoved.getId()));

        List<Video> videosOfKeptUser = videoDao.findVideosForUserId(userToBeKept.getId());
        assertEquals(2, videosOfKeptUser.size());

        List<Video> videosOfRemovedUser = videoDao.findVideosForUserId(userToBeRemoved.getId());
        assertEquals(0, videosOfRemovedUser.size());
    }
}
