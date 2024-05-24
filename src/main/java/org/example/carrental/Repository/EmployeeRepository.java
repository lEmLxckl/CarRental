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

    @Autowired
    JdbcTemplate template;

    //Find employee hvor username er ? og user_password er ?... Vi bruger den login
    public Employee findByUserAndPassword(String username, String user_password) {
        String sql = "SELECT * FROM Employee WHERE username=? AND user_password=?";
        RowMapper rowMapper = new BeanPropertyRowMapper(Employee.class);
        List<Employee> employees = template.query(sql, rowMapper, username, user_password);
        if ((employees.size()) == 1) {
            return employees.get(0);
        } else {
            return null;
        }

    }

    // Returner en liste af alle Employees
    public List<Employee> fetchAll() {
        String sql = "SELECT * FROM Employee";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        return template.query(sql, rowMapper);
    }

    //tilføj Employee
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO Employee (username, user_password, full_name, email, phone, is_active, is_admin) VALUES (?,?,?,?,?,?,?)";
        template.update(sql, employee.getUsername(), employee.getUser_password(), employee.getFull_name(), employee.getEmail(), employee.getPhone(), employee.getIs_active(), employee.getIs_admin());

    }
    // input username for at finde en employee
    public boolean doesTheUserExsit(String username){
        String sql = "SELECT * FROM Employee WHERE Username=?";
        RowMapper<Employee>rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee>employees = template.query(sql,rowMapper,username);
        return !employees.isEmpty();
    }
    // Fyr en Employee hvor username er ?
    public void fireEmployee(String username){
        String sql = "UPDATE employee SET is_active = 0 WHERE username = ?";
        template.update(sql, username);

    }
    // Opdater Employee
    public void updateEmployee(Employee employee){
        String sql = "UPDATE employee SET user_password = ?, full_name= ?, email= ?, phone= ?, is_active= ?, is_admin = ? where username=?";
        template.update(sql,employee.getUser_password(), employee.getFull_name(), employee.getEmail(), employee.getPhone(), employee.getIs_active(), employee.getIs_admin(), employee.getUsername());

    }
    // Find en Employee ved at kigge på username
    public Employee findByUsername(String username){
        String sql = "Select * FROM employee WHERE username = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> users = template.query(sql, rowMapper, username);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }

    }

    // Find admin hvor username er ?
    public Employee findAdmin(String username){
        String sql = "SELECT * FROM employee WHERE is_admin=1 and username = ?";
        RowMapper rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> users = template.query(sql, rowMapper, username);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }

    }
}