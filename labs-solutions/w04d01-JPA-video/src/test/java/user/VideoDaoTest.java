package user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class VideoDaoTest {

    VideoDao videoDao;

    TagDao tagDao;

    @BeforeEach
    void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        videoDao = new VideoDao(entityManagerFactory);
        tagDao = new TagDao(entityManagerFactory);
    }

    @Test
    void testSaveVideo() {
        Video video = new Video("My first video", LocalTime.of(1, 23, 31));
        videoDao.saveVideo(video);

        Video other = videoDao.findVideo(video.getId());

        assertEquals("My first video", other.getTitle());
        assertEquals(LocalTime.of(1, 23, 31), other.getLength());
    }

    @Test
    void testFindVideoWithTags() {
        Video video = new Video("My first video", LocalTime.of(1, 23, 31));
        videoDao.saveVideo(video);
        Tag tag = new Tag("myvideo");
        tagDao.saveTag(tag);
        videoDao.saveTagToVideo(video.getId(), tag.getId());

        Video other = videoDao.findVideoByIdWithTags(video.getId());

        assertEquals("My first video", other.getTitle());
        assertEquals(LocalTime.of(1, 23, 31), other.getLength());
        assertEquals(1, other.getTags().size());
    }
}
