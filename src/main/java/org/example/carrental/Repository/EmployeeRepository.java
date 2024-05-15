package org.example.carrental.Repository;

import org.example.carrental.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Employee> employeeRowMapper = new BeanPropertyRowMapper<>(Employee.class);

    @Autowired
    // constructor til at injicere JdbcTemplate-dependency
    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // finder alle employees
    public List<Employee> getEmployees() {
        String query = "SELECT * FROM employees;";
        return jdbcTemplate.query(query, employeeRowMapper);
    }

    // finder employess via id
    public Employee getEmployee(int id) {
        String query = "SELECT * FROM employees WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, employeeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // sletter employees via id
    public void delete(int id) {
        String query = "DELETE FROM employees WHERE id = ?;";
        jdbcTemplate.update(query, id);
    }


    public Employee findByUserName(String userName) {
        //Metode til at finde employees by username
        // den bruger SQL query til at søge efter employees by username column
        String query = "SELECT * FROM employees WHERE username = ?";
        List<Employee> employees = jdbcTemplate.query(query, new Object[]{userName}, employeeRowMapper);
        // Returnere den første employee hvis fundet,
        // ellers returnere den null hvis listen er empty
        return employees.isEmpty() ? null : employees.get(0);
    }

    public void saveOrUpdate(Employee employee) {
        if (employee.getId() == 0) {
            String insertQuery = "INSERT INTO employees(username, userPassword, usertype) VALUES (?, ?, ?);";
            jdbcTemplate.update(insertQuery, employee.getUserName(), employee.getUserPassword(), employee.getUsertype());
        } else {
            String updateQuery = "UPDATE employees SET username = ?, userpassword = ?, usertype = ? WHERE id = ?";
            jdbcTemplate.update(updateQuery, employee.getUserName(), employee.getUserPassword(), employee.getId(), employee.getUsertype());
        }
    }


}
