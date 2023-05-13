package jpqlqueries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository repository;

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
        repository.save(person1);

        Person person2 = new Person("person2", 34);
        Child child6 = new Child("child6", 2019);
        person2.addChild(child6);
        repository.save(person2);

        Person person3 = new Person("person3", 42);
        Child child7 = new Child("child7", 2022);
        Child child8 = new Child("child8", 2018);
        person3.addChild(child7);
        person3.addChild(child8);
        repository.save(person3);

        Person person4 = new Person("person4", 19);
        repository.save(person4);

        Person person5 = new Person("person5", 24);
        Child child9 = new Child("child9", 2021);
        person5.addChild(child9);
        repository.save(person5);

        Person person6 = new Person("person6", 30);
        Child child10 = new Child("child10", 2017);
        Child child11 = new Child("child11", 2022);
        person6.addChild(child10);
        person6.addChild(child11);
        repository.save(person6);
    }

    @Test
    void testQuery2() {
        List<Person> expected = repository.findPeopleWithChildrenMoreThanOne();

        assertEquals(3, expected.size());
    }

    @Test
    void testQuery3() {
        Person expected = repository.findPersonWithMostChildren();

        assertEquals("person1", expected.getName());
    }

    @Test
    void testQuery5() {
        Person expected = repository.findPersonByChildName("child6");

        assertEquals("person2", expected.getName());
    }

    @Test
    void testQuery6() {
        double expected = repository.getAverageNumberOfChild();

        assertEquals(1.83, expected, 0.05);
    }
}