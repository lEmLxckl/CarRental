package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Employee;
import org.example.carrental.model.Usertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    private final Map<String, Integer> loginAttempts = new HashMap<>();

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("adminlogin");
        return "redirect:/";
    }
    //Homepage
    @GetMapping("/Home")
    public String home(HttpSession session, Model model) {
        String value = (String) session.getAttribute("username");
        model.addAttribute("username", value);
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        return "home/Home";
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
    private String redirectToUserSpecificPage(Usertype userType) {
        return switch (userType) {
            case DATAREGISTRATOR -> "redirect:/dataRegistration";
            case DAMAGEREPORTER -> "redirect:/damageAndPickUp";
            case BUSINESSDEVELOPER -> "redirect:/businessDevelopment";
            case ADMIN -> "redirect:menu";
            //default -> "redirect:/dashboard";
        };
    }
}
    /*@GetMapping("/")
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

    private String redirectToUserSpecificPage(Usertype userType) {
        return switch (userType) {
            case DATAREGISTRATOR -> "redirect:/dataRegistration";
            case DAMAGEREPORTER -> "redirect:/damageAndPickUp";
            case BUSINESSDEVELOPER -> "redirect:/businessDevelopment";
            case ADMIN -> "redirect:menu";
            //default -> "redirect:/dashboard";
        };
    }
    */

