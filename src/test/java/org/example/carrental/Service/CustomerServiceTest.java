package org.example.carrental.Service;

import org.example.carrental.Repository.CustomerRepository;
import org.example.carrental.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;  // replace with your actual repository interface/class

    @InjectMocks
    private CustomerService customerService;  // replace with your actual service class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setCustomer_id(1);
        customer.setFull_name("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("123456789");
        customer.setAddress("123 Main St");
        customer.setCpr("123456-7890");

        // When
        customerService.createCustomer(customer);

        // Then
        verify(customerRepository).createCustomer(customer);
    }
}