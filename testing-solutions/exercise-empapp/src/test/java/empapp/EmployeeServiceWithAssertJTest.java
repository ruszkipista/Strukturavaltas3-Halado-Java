package empapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;

import org.junit.jupiter.api.Test;

public class EmployeeServiceWithAssertJTest {

    @Test
    void testListEmployees() {
        List<Employee> employees = new EmployeeService().listEmployees();

        Employee employee = employees.get(0);

        assertThat(employee.getName()).isEqualTo("John Doe");
        assertThat(employee.getName())
                .startsWith("John")
                .endsWith("Doe");

        assertThat(employees)
                .hasSize(2)
                .extracting(Employee::getName)
                .contains("John Doe");

        assertThat(employees)
                .as("Contains two elements, John is the first")
                .hasSize(2)
                .extracting(Employee::getName, Employee::getYearOfBirth)
                .contains(tuple("John Doe", 1970));

//         assumeThat(employee.getName()).isEqualTo("xxx");
    }
}
