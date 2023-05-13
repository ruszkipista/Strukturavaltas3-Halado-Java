package empapp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.startsWith;

import org.junit.jupiter.api.Test;

public class EmployeeWithHamcrestTest {

    @Test
    void testAge() {
        Employee employee = new Employee("John Doe", 1970);

        //assertEquals(30, employee.getAge(2000));
        assertThat(employee.getAge(2000), equalTo(30));

        assertThat(employee.getName(), startsWith("John"));
        assertThat(employee, anyOf(hasProperty("name", startsWith("John")),
                hasProperty("yearOfBirth", equalTo(1971)))
                );
    }
}
