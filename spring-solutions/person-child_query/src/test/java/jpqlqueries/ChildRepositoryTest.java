package jpqlqueries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ChildRepositoryTest {

    @Autowired
    ChildRepository childRepository;

    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    void init() {
        Person person1 = new Person("person1", 39);
        Child child1 = new Child("child1", 2009);
        Child child2 = new Child("child2", 2012);
        Child child3 = new Child("child3", 2015);
        Child child4 = new Child("child4", 2017);
        Child child5 = new Child("child5", 2020);
        person1.addChild(child1);
        person1.addChild(child2);
        person1.addChild(child3);
        person1.addChild(child4);
        person1.addChild(child5);
        personRepository.save(person1);

        Person person2 = new Person("person2", 34);
        Child child6 = new Child("child6", 2019);
        person2.addChild(child6);
        personRepository.save(person2);

        Person person3 = new Person("person3", 42);
        Child child7 = new Child("child7", 2022);
        Child child8 = new Child("child8", 2018);
        person3.addChild(child7);
        person3.addChild(child8);
        personRepository.save(person3);

        Person person4 = new Person("person4", 19);
        personRepository.save(person4);

        Person person5 = new Person("person5", 24);
        Child child9 = new Child("child9", 2021);
        person5.addChild(child9);
        personRepository.save(person5);

        Person person6 = new Person("person6", 30);
        Child child10 = new Child("child10", 2017);
        Child child11 = new Child("child11", 2022);
        person6.addChild(child10);
        person6.addChild(child11);
        personRepository.save(person6);
    }

    @Test
    void testQuery1() {
        List<Child> expected = childRepository.findChildrenByYearOfBirthAfter(2016);

        assertEquals(8, expected.size());
    }

    @Test
    void testQuery4() {
        Child expected = childRepository.findChildWithParentNameAndYearOfBirthGiven("person1", 2015);

        assertEquals("child3", expected.getName());
    }

    @Test
    void testQuery7() {
        List<Child> expected = childRepository.findChildrenWithMostSiblings();

        assertEquals(5, expected.size());
    }
}