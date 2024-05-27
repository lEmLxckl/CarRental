package org.example.carrental.Repository;

import org.example.carrental.model.Employee;
import org.example.carrental.model.Usertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeRepository {

    @Autowired
    JdbcTemplate template;

    private final RowMapper<Employee> employeeRowMapper = new RowMapper<Employee>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setUsername(rs.getString("username"));
            employee.setUser_password(rs.getString("user_password"));
            employee.setFull_name(rs.getString("full_name"));
            employee.setEmail(rs.getString("email"));
            employee.setPhone(rs.getString("phone"));
            employee.setIs_active(rs.getInt("is_active"));
            employee.setIs_admin(rs.getInt("is_admin"));
            employee.setUsertype(Usertype.fromString(rs.getString("usertype")));
            return employee;
        }
    };

    public Employee findByUserAndPassword(String username, String user_password) {
        String sql = "SELECT * FROM Employee WHERE username=? AND user_password=?";
        List<Employee> employees = template.query(sql, employeeRowMapper, username, user_password);
        if ((employees.size()) == 1) {
            return employees.get(0);
        } else {
            return null;
        }
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM Employee";
        return template.query(sql, employeeRowMapper);
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO Employee (username, user_password, full_name, email, phone, usertype, is_active, is_admin) VALUES (?,?,?,?,?,?,?,?)";
        template.update(sql, employee.getUsername(), employee.getUser_password(), employee.getFull_name(), employee.getEmail(), employee.getPhone(), employee.getUsertype().toString(), employee.getIs_active(), employee.getIs_admin());
    }

    public void deletEmployee(String username) {
        String sql = "UPDATE employee SET is_active = 0 WHERE username = ?";
        template.update(sql, username);
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET user_password = ?, full_name= ?, email= ?, phone= ?, is_active= ?, is_admin = ?, usertype = ? where username=?";
        template.update(sql, employee.getUser_password(), employee.getFull_name(), employee.getEmail(), employee.getPhone(), employee.getIs_active(), employee.getIs_admin(), employee.getUsertype().toString(), employee.getUsername());
    }

    public Employee findByUsername(String username) {
        String sql = "Select * FROM employee WHERE username = ?";
        List<Employee> users = template.query(sql, employeeRowMapper, username);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public Employee findAdmin(String username) {
        String sql = "SELECT * FROM employee WHERE is_admin=1 and username = ?";
        List<Employee> users = template.query(sql, employeeRowMapper, username);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
