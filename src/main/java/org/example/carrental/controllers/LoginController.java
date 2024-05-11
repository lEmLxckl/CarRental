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

@Controller
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "home/employeeLogin";
    }

    @PostMapping("/employeeLogin")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("userPassword") String userPassword,
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
        Employee loggedInUser = (Employee) session.getAttribute("employee");

        if (loggedInUser == null) {
            // der er ingen user i session, redirect til login page
            return "redirect:/";
        }
        model.addAttribute("employee", loggedInUser);
        return "home/dashboard";
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
