package user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class UserDaoTest {

    UserDao userDao;

    VideoDao videoDao;

    TagDao tagDao;

    CommentDao commentDao;

    @BeforeEach
    void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        userDao = new UserDao(entityManagerFactory);
        videoDao = new VideoDao(entityManagerFactory);
        tagDao = new TagDao(entityManagerFactory);
        commentDao = new CommentDao(entityManagerFactory);
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
    void testFindUserWithVideos() {
        User user = new User("Jack", LocalDate.of(2022,11,11));
        userDao.saveUser(user);
        user = new User("John", LocalDate.of(2021,11,11));
        userDao.saveUser(user);
        Video video = new Video("My first video", LocalTime.of(1, 23, 31));
        videoDao.saveVideo(video);

        userDao.saveVideoToUser(user.getId(), video.getId());

        User userCopy = userDao.findUserByIdWithVideos(user.getId());

        assertEquals("John", userCopy.getName());
        assertEquals(LocalDate.of(2021, 11, 11), userCopy.getRegistrationDate());
        assertEquals(1, userCopy.getVideos().size());
    }

    @Test
    void testFindUserWithComments() {
        User user = new User("John", LocalDate.of(2021,11,11));
        userDao.saveUser(user);
        Comment comment = new Comment("This is my first video and I like it very much");
        commentDao.saveComment(comment);
        userDao.saveCommentToUser(user.getId(), comment.getId());

        User other = userDao.findUserByIdWithComments(user.getId());

        assertEquals("John", other.getName());
        assertEquals(LocalDate.of(2021, 11, 11), other.getRegistrationDate());
        assertEquals(1, other.getComments().size());
    }

    @Test
    void testFindUserWithEVERYTHING() {
        User user = new User("John", LocalDate.of(2021,11,11));
        userDao.saveUser(user);

        Comment comment = new Comment("This is my first video and I like it very much");
        commentDao.saveComment(comment);
        userDao.saveCommentToUser(user.getId(), comment.getId());
        comment = new Comment("Hope others like it too");
        commentDao.saveComment(comment);
        userDao.saveCommentToUser(user.getId(), comment.getId());

        Video video = new Video("My first video", LocalTime.of(1, 23, 31));
        videoDao.saveVideo(video);
        userDao.saveVideoToUser(user.getId(), video.getId());
        Tag tag = new Tag("tag1");
        tagDao.saveTag(tag);
        videoDao.saveTagToVideo(video.getId(), tag.getId());

        video = new Video("My second video", LocalTime.of(1, 45, 31));
        videoDao.saveVideo(video);
        userDao.saveVideoToUser(user.getId(), video.getId());
        tag = new Tag("tag2");
        tagDao.saveTag(tag);
        videoDao.saveTagToVideo(video.getId(), tag.getId());
        tag = new Tag("tag3");
        tagDao.saveTag(tag);
        videoDao.saveTagToVideo(video.getId(), tag.getId());

        User userCopy = userDao.findUserByIdWithEVERYTHING(user.getId());

        assertEquals("John", userCopy.getName());
        assertEquals(LocalDate.of(2021, 11, 11), userCopy.getRegistrationDate());
        assertEquals(2, userCopy.getComments().size());
        assertEquals(2, userCopy.getVideos().size());
        assertEquals(2, List.copyOf(userCopy.getVideos()).get(0).getTags().size());
    }
}