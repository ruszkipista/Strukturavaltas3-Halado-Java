package empapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

//@Tag("service")
@ServiceTest
public class EmployeeServiceTest {

    @Test
    void testListEmployeeNames() {
        assertEquals(List.of("John Doe", "Jane Doe"), new EmployeeService().listEmployeeNames());
    }

    @Test
    void testListEmployees() {
        assertEquals(List.of(new Employee("John Doe", 1970),
                new Employee("Jane Doe", 1980)), new EmployeeService().listEmployees());

        assertEquals(List.of("John Doe", "Jane Doe"), new EmployeeService().listEmployees().stream()
            .map(Employee::getName).collect(Collectors.toList()), "Employee lists are different");
    }

    @Test
    void testAssumption() {
        EmployeeService employeeService = new EmployeeService();

        assumeTrue(employeeService != null);

        assertEquals(List.of(new Employee("John Doe", 1970),
                new Employee("Jane Doe", 1980)), employeeService.listEmployees());
    }

    @Test
    void testCalculateYearlyReport() {
//        assertTimeout(Duration.ofSeconds(3), () -> new EmployeeService().calculateYearlyReport());
//        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> new EmployeeService().calculateYearlyReport());
    }
}
