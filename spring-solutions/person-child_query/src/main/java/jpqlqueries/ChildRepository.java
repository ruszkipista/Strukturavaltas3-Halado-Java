package jpqlqueries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChildRepository extends JpaRepository<Child, Long> {

    // azokat a gyerekeket, akik egy megadott év után születtek
    @Query("select c from Child c where c.yearOfBirth > :year")
    List<Child> findChildrenByYearOfBirthAfter(@Param("year") int year);

    // azt a gyereket, akinek a szülője a megadott nevű, és a megadott évben született (a gyerek)
    @Query("select c from Child c where c.person.name = :name and c.yearOfBirth = :year")
    Child findChildWithParentNameAndYearOfBirthGiven(@Param("name") String name, @Param("year") int year);

    // azokat a gyerekeket, akik a legtöbben vannak testvérek
    @Query("select c from Child c where size(c.person.children) = (select max(size(p.children)) from Person p)")
    List<Child> findChildrenWithMostSiblings();
}