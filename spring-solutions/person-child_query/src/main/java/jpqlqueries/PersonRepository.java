package jpqlqueries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // azokat a felnőtteket, akiknek több gyerekük is van
    @Query("select distinct p from Person p left join fetch p.children c where size(c) > 1")
    List<Person> findPeopleWithChildrenMoreThanOne();

    //azt a felnőttet, akinek a legtöbb gyereke van
    @Query("select distinct p from Person p left join fetch p.children c where size(c) = (select max(size(q.children)) from Person q)")
    Person findPersonWithMostChildren();

    // azt a szülőt, akihez a paraméterül megadott nevű gyerek tartozik
//    @Query("select distinct p from Person p left join fetch p.children c where c.name = :name")
    @Query("select c.person from Child c where c.name = :name")
    Person findPersonByChildName(@Param("name") String name);

    // az átlagos gyerekszámot
    @Query("select avg(size(p.children)) from Person p")
    double getAverageNumberOfChild();
}