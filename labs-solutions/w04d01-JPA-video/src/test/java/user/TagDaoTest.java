package user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class TagDaoTest {

    TagDao tagDao;

    @BeforeEach
    void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        tagDao = new TagDao(entityManagerFactory);
    }

    @Test
    void testSaveTag() {
        Tag tag = new Tag("myvideo");
        tagDao.saveTag(tag);

        Tag other = tagDao.findTag(tag.getId());

        assertEquals("#myvideo", other.getText());
    }
}