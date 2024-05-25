package org.example.carrental.Repository;

import org.example.carrental.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@ComponentScan(basePackages = "com.your.package")  // replace with your actual package
public class CustomerRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerRepository customerRepository;  // replace with your actual repository class

    @BeforeEach
    void setUp() {
        // Create the customer table
        jdbcTemplate.execute("CREATE TABLE customer (" +
                "customer_id INT PRIMARY KEY, " +
                "full_name VARCHAR(255), " +
                "email VARCHAR(255), " +
                "phone VARCHAR(255), " +
                "address VARCHAR(255), " +
                "cpr VARCHAR(255))");
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
        customerRepository.createCustomer(customer);

        // Then
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customer WHERE customer_id = ?", Integer.class, customer.getCustomer_id());
        assertThat(count).isEqualTo(1);

        // Verify the inserted customer details
        Customer retrievedCustomer = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customer_id = ?",
                new Object[]{customer.getCustomer_id()},
                (rs, rowNum) -> {
                    Customer c = new Customer();
                    c.setCustomer_id(rs.getInt("customer_id"));
                    c.setFull_name(rs.getString("full_name"));
                    c.setEmail(rs.getString("email"));
                    c.setPhone(rs.getString("phone"));
                    c.setAddress(rs.getString("address"));
                    c.setCpr(rs.getString("cpr"));
                    return c;
                }
        );

        assertThat(retrievedCustomer).isNotNull();
        assertThat(retrievedCustomer.getFull_name()).isEqualTo(customer.getFull_name());
        assertThat(retrievedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(retrievedCustomer.getPhone()).isEqualTo(customer.getPhone());
        assertThat(retrievedCustomer.getAddress()).isEqualTo(customer.getAddress());
        assertThat(retrievedCustomer.getCpr()).isEqualTo(customer.getCpr());
    }
}