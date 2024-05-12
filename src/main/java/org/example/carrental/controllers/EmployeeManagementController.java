package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeManagementController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "home/employeeLogin";
    }

    @PostMapping("/employeeLogin")
    public String login(@RequestParam String userName,
                        @RequestParam String userPassword,
                        Model model, HttpSession session) {
        Employee loggedInEmployee = employeeService.authenticate(userName, userPassword);

        if (loggedInEmployee != null) {
            session.setAttribute("employee", loggedInEmployee);
            return "redirect:/dashboard"; // sender til dashboard, hvor man kan vælge en usertype
        } else {
            model.addAttribute("loginError", "Error logging in. Please check your username and password. ");
            return "home/employeeLogin";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
      Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "home/dashboard";
        } else {
            return "redirect:/";
        }

    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("newEmployee", new Employee());
        return "home/registration";
    }


    @PostMapping("/registration")
    public String createNewEmployee(@ModelAttribute("newEmployee") Employee newEmployee, Model model) {
        Employee existingEmployee = employeeService.findEmployeeByUsername(newEmployee.getUserName());
        if (existingEmployee != null) {
            model.addAttribute("registrationError", "An employee with that username already exists. ");
            return "home/registration";
        }

        employeeService.saveEmployee(newEmployee);
        model.addAttribute("registrationSuccess", "Employee registered successfully. ");
        return "home/registration";

    }

    @GetMapping("/deleteEmployee")
    public String showAllEmployees(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null && employeeService.isAdmin(employee.getId())) {
            List<Employee> employees = employeeService.getEmployees();
            model.addAttribute("employees", employees);
            return "home/employeeList";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("id") int id, HttpSession session) {
        Employee requestingEmployee = (Employee) session.getAttribute("employee");
        if (requestingEmployee != null && employeeService.isAdmin(requestingEmployee.getId())) {
           System.out.println("Admin deleting employee with ID: " + id);
            employeeService.delete(id);
            return "redirect:/dashboard";
        } else {
            System.out.println("Unauthorized deletion attempt by employee with ID: " + requestingEmployee.getId());
        }
        return "redirect:/employees"; // Redirect back to the employee list
    }

    @GetMapping("/employeeList")
    public String showAllEmployees(Model model) {
        List<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);
        return "home/employeeList"; // Ensure the view name matches your HTML file
    }

    @GetMapping("/dataRegistration")
    public String showDataRegistration(Model model) {
        return "home/dataRegistration";
    }

    @GetMapping("/damageReport")
    public String showDamageReport(Model model) {
        return "home/damageReport";
    }

    @GetMapping("/businessDevelopment")
    public String showBusinessDevelopment(Model model) {
        return "home/businessDevelopment";
    }
}
