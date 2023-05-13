package empapp;

import static empapp.EmployeeWithNameMatcher.employeeWithName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.startsWith;

import java.util.List;

import org.junit.jupiter.api.Test;

public class EmployeeServiceWithHamcrestTest {

    @Test
    void testListEmployees() {
        List<Employee> employees = new EmployeeService().listEmployees();
        assertThat(employees, hasItem(
                hasProperty("name", equalTo("John Doe"))
        ));
    }

    @Test
    void testListEmployeesWithMatcher() {
        List<Employee> employees = new EmployeeService().listEmployees();

        assertThat(employees, hasItem(
                employeeWithName(equalTo("John Doe"))
        ));

        assertThat(employees.get(0), employeeWithName(startsWith("Joh")));
    }
}
