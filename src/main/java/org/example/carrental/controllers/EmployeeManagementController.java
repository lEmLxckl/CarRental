package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Employee;
import org.example.carrental.model.Usertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class EmployeeManagementController {

    @Autowired
    private EmployeeService employeeService;

    private final Map<String, Integer> loginAttempts = new HashMap<>();

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "home/employeeLogin";
    }

    @PostMapping("/employeeLogin")
    public String login(@RequestParam String userName,
                        @RequestParam String userPassword,
                        Model model, HttpSession session) {
        loginAttempts.put(userName, loginAttempts.getOrDefault(userName, 0) + 1);

        Employee loggedInEmployee = employeeService.authenticate(userName, userPassword);

        if (loggedInEmployee != null) {
            session.setAttribute("employee", loggedInEmployee);
            session.setAttribute("userType", loggedInEmployee.getUsertype());
            // return "redirect:/dashboard";
            loginAttempts.put(userName, 0); // Reset login attempts on successful login
            return redirectToUserSpecificPage(loggedInEmployee.getUsertype()); // sender til dashboard, hvor man kan vælge en usertype
        } else {
            if (loginAttempts.get(userName) >= 3) {
                model.addAttribute("loginError", "Error logging in. Please check your username and password. ");
            }
            return "home/employeeLogin";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        Usertype userType = (Usertype) session.getAttribute("userType");

        if (employee != null) {
            model.addAttribute("employee", employee);
            model.addAttribute("userType", userType);
            return "home/dashboard";
        } else {
            model.addAttribute("employeeNotFound", true);
            return "redirect:/";
        }
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null && employee.getUsertype() == Usertype.ADMIN) {
            model.addAttribute("newEmployee", new Employee());
            model.addAttribute("allUserTypes", Usertype.values());
            return "home/registration";
        } else {
            return "redirect:/dashboard";

        }

    }


    @PostMapping("/registration")
    public String createNewEmployee(@ModelAttribute("newEmployee") Employee newEmployee, Model model, HttpSession session) {
        //System.out.println("Usertype: " + newEmployee.getUsertype());
        Employee employee = (Employee) session.getAttribute("employee");

        // Check if the current user is an admin
        if (employee != null && employee.getUsertype() == Usertype.ADMIN) {
            Employee existingEmployee = employeeService.findEmployeeByUsername(newEmployee.getUserName());
            if (existingEmployee != null) {
                model.addAttribute("registrationError", "An employee with that username already exists.");
                return "home/registration";
            }
            employeeService.saveEmployee(newEmployee);
            model.addAttribute("registrationSuccess", "Employee registered successfully.");
            return "home/registration";
        } else {
            return "redirect:/dashboard"; // Redirect non-admin users to dashboard
        }
    }

    @GetMapping("/deleteEmployee")
    public String showAllEmployees(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null && employee.getUsertype() == Usertype.ADMIN) {
         List<Employee> employees = employeeService.getEmployees();
            model.addAttribute("employees", employees);
            return "home/employeeList";
        } else {
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("id") int id, HttpSession session) {
        Employee requestingEmployee = (Employee) session.getAttribute("employee");
        if (requestingEmployee != null && requestingEmployee.getUsertype() == Usertype.ADMIN) {
           System.out.println("Admin deleting employee with ID: " + id);
            employeeService.delete(id);
            return "redirect:/dashboard";
        } else {
            System.out.println("Unauthorized deletion attempt by employee with ID: " + requestingEmployee.getId());
        }
        //ændret herunder 18:57
        return "redirect:/dashboard"; // Redirect back to the employee list
    }

    @GetMapping("/employeeList")
    public String showAllEmployees(Model model, HttpSession session, Employee employee) {
        employee = (Employee) session.getAttribute("employee");
        if (employee != null && employee.getUsertype() == Usertype.ADMIN) {
            List<Employee> employees = employeeService.getEmployees();
            model.addAttribute("employees", employees);
            return "home/employeeList";
        } else {
            return "redirect:/dashboard";
        }
    }

    /* @GetMapping("/employeeList")
    public String showAllEmployees(Model model) {
        List<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);
        return "home/employeeList"; // Ensure the view name matches your HTML file
    }*/

    @GetMapping("/updateEmployee")
    public String showUpdateForm(@RequestParam("id") int id, Model model, HttpSession session) {
        Employee requestingEmployee = (Employee) session.getAttribute("employee");
        if (requestingEmployee != null && requestingEmployee.getUsertype() == Usertype.ADMIN) {
            Employee employee = employeeService.getEmployee(id);
            if (employee != null) {
                model.addAttribute("employee", employee);
                model.addAttribute("allUserTypes", Usertype.values());
                return "home/updateEmployee";
            }
        }
        return "redirect:/dashboard"; // Redirect non-admin users to dashboard
    }
    /*
    @GetMapping("/updateEmployee")
    public String showUpdateForm(@RequestParam("id") int id, Model model) {
        Employee employee = employeeService.getEmployee(id);
            if (employee != null && employee.getUsertype() == Usertype.ADMIN) {
            model.addAttribute("employee", employee);
            model.addAttribute("allUserTypes", Usertype.values());
            return "home/updateEmployee";
        }
        return "redirect:/dashboard";

}*/

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute("employee") Employee employee, Model model, HttpSession session) {
        Employee requestingEmployee = (Employee) session.getAttribute("employee");
        if (requestingEmployee != null && requestingEmployee.getUsertype() == Usertype.ADMIN) {
            employeeService.saveEmployee(employee);
            model.addAttribute("updateSuccess", "Employee updated successfully.");
        }
        return "redirect:/dashboard"; // Redirect non-admin users to dashboard
    }



    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/dataRegistration")
    public String showDataRegistration(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null && (employee.getUsertype() == Usertype.DATAREGISTRATOR || employee.getUsertype() == Usertype.ADMIN)) {
            return "home/dataRegistration";
        } else {
            model.addAttribute("accessDenied", "You dont have permission to acces this page. ");
            return "redirect:/dashboard";
        }
    }


    @GetMapping("/businessDevelopment")
    public String showBusinessDevelopment(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null && (employee.getUsertype() == Usertype.BUSINESSDEVELOPER || employee.getUsertype() == Usertype.ADMIN)) {
            return "home/businessDevelopment";
        } else {
            model.addAttribute("accessDenied", "You dont have permission to acces this page. ");
            return "redirect:/dashboard";
        }
    }



    private String redirectToUserSpecificPage(Usertype userType) {
        return switch (userType) {
            case DATAREGISTRATOR -> "redirect:/dataRegistration";
            case DAMAGEREPORTER -> "redirect:/damageAndPickUp";
            case BUSINESSDEVELOPER -> "redirect:/businessDevelopment";
            case ADMIN -> "redirect:dashboard";
            default -> "redirect:/dashboard";
        };
    }

}
