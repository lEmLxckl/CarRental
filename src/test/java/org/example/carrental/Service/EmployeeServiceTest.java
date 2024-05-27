package org.example.carrental.Service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.example.carrental.Repository.EmployeeRepository;
import org.example.carrental.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepo;  // replace with your actual repository interface/class

    @InjectMocks
    private EmployeeService employeeService;  // replace with your actual service class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        // Given
        Employee employee1 = new Employee();
        employee1.setIs_admin(1);
        employee1.setFull_name("Alice Johnson");
        employee1.setEmail("alice.johnson@example.com");
        employee1.setPhone("987654321");

        Employee employee2 = new Employee();
        employee2.setIs_admin(2);
        employee2.setFull_name("Bob Smith");
        employee2.setEmail("bob.smith@example.com");
        employee2.setPhone("123456789");


        List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);

        // When
        when(employeeRepo.getAllEmployees()).thenReturn(expectedEmployees);
        List<Employee> actualEmployees = employeeService.getAllEmployees();

        // Then
        verify(employeeRepo).getAllEmployees();
        assertThat(actualEmployees).isEqualTo(expectedEmployees);
    }
}