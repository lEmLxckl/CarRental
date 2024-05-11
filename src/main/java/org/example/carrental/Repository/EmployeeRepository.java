package org.example.carrental.Repository;

import org.example.carrental.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;
    // custom rowmapper, som opretter et nyt Employee-objekt
    // og den setter dens properties fra database rowsne
    private final RowMapper<Employee> employeeRowMapper = (rs, rowNum) -> {
        Employee employee = new Employee();
        employee.setId(rs.getString("id"));
        employee.setUserName(rs.getString("username"));
        employee.setUserPassword(rs.getString("password"));
        return employee;
    };

    @Autowired
    // constructor til at injicere JdbcTemplate-dependency
    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    public void save(Employee newEmployee) {
        // Metod til at gemme en ny employee i databasen
        // Den bruger SQL insert statement
        String query = "INSERT INTO employees (id, username, userpassword) VALUES (?, ?, ?) ";
        jdbcTemplate.update(query, newEmployee.getId(), newEmployee.getUserName(), newEmployee.getUserPassword());
    }

    public void  update(Employee employee) {
        // Metode til at opdatere en existerende employee's username og password i databasen
        String query = "UPDATE employees SET username = ?, userpassword = ? WHERE id = ?";
        jdbcTemplate.update(query, employee.getUserName(), employee.getUserPassword(), employee.getId());
    }

    public void delete(String id) {
        // Metode til at delete employee fra databasen by deres ID
        String query = "DELETE FROM employees WHERE id = ?";
        jdbcTemplate.update(query,id);
    }
}
