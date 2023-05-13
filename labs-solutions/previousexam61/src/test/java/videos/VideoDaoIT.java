package videos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class VideoDaoIT {
    EntityManagerFactory entityManagerFactory;
    VideoDao videoDao;
    UserDao userDao;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        videoDao = new VideoDao(entityManagerFactory);
        userDao = new UserDao(entityManagerFactory);
    }
 
    @AfterEach
    void close(){
        entityManagerFactory.close();
    }

    @Test
    void testSaveVideo() {
        User user = new User("ruszkipista", LocalDate.parse("2023-03-04"));
        userDao.saveUser(user);

        Video video = new Video("my life in 2 mins", user);
        assertNull(video.getId());

        videoDao.saveVideoOfUser(video);
        assertNotNull(video.getId());

        Video videoCopy = videoDao.findVideoById(video.getId());
        assertEquals("my life in 2 mins", videoCopy.getTitle());
        assertNotNull(videoCopy.getUser());

        User userCopy = userDao.findUserByIdWithVideos(user.getId());
        assertEquals(1, userCopy.getVideos().size());

        video = new Video("my happy moments", user);
        videoDao.saveVideoOfUser(video);

        userCopy = userDao.findUserByIdWithVideos(user.getId());
        assertEquals(2, userCopy.getVideos().size());
    }

    @Test
    void remove1VideoById_removed(){
        User user = new User("user", LocalDate.parse("2023-01-01"));
        userDao.saveUser(user);
        userDao.updateUserWithVideo(user.getId(), new Video("videoR1"));
        Video videoToRemove = new Video("videoR2");
        userDao.updateUserWithVideo(user.getId(), videoToRemove);
        userDao.updateUserWithVideo(user.getId(), new Video("videoR3"));

        videoDao.removeVideoById(videoToRemove.getId());

        List<Video> videosOfKeptUser = videoDao.findVideosForUserId(user.getId());
        assertEquals(2, videosOfKeptUser.size());
    }
}
