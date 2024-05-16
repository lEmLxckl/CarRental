package org.example.carrental.Service;

import org.example.carrental.Repository.EmployeeRepository;
import org.example.carrental.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {
    private  final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public List<Employee> getEmployees() {
        return employeeRepository.getEmployees();
    }

    public Employee getEmployee(int id) {
        return employeeRepository.getEmployee(id);
    }

    public void delete(int id) {
        employeeRepository.delete(id);
    }

    public Employee authenticate(String userName, String passWord) {
        // altså leder den efter employee i databasen, så hvis employee username og password ikke
        // er lig med null så kunne man autentificere brugeren
        Employee employee = employeeRepository.findByUserName(userName);
        if (employee != null && employee.getUserPassword().equals(passWord)) {
            return employee; // succes autentificering
        }
        return null; // employee kunne ikke autentificeres
    }

    public Employee findEmployeeByUsername(String userName) {
        // tjekker hvis employee exists
        return  employeeRepository.findByUserName(userName);
    }

    public void saveEmployee(Employee newEmployee) {
        // gemmer den nye employee, så der ikke er to af de samme employees
        // den finder alle employees by username, hvis den returnere null gemmer
        //den den nye employee
        if (findEmployeeByUsername(newEmployee.getUserName()) == null) {
            employeeRepository.saveOrUpdate(newEmployee);
        }
    }
}
