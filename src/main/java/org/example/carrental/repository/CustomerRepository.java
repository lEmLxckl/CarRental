package org.example.carrental.repository;

import org.example.carrental.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> getAllCustomers() {
        String query = "SELECT * FROM customer";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public Customer getCustomerById(int id) {
        String query = "SELECT * FROM customer WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public void deleteById(int id) {
        String query = "DELETE FROM customer WHERE customer_id = ?";
        jdbcTemplate.update(query, id);
    }

    public Customer save(Customer customer) {
        String query = "INSERT INTO customer (first_name, last_name, email, phone, address) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                customer.getPhone(), customer.getAddress());
        return customer;
    }

    public void update(int id, Customer customer) {
        String query = "UPDATE customer SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ? " +
                "WHERE customer_id = ?";
        jdbcTemplate.update(query, customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                customer.getPhone(), customer.getAddress(), customer.getCustomerID());
    }
}