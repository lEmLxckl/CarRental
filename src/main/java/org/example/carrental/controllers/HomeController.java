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

    @Autowired
    EmployeeService employeeService;
    //Start page
    @GetMapping("/")
    public String index() {
        return "home/index";
    }
    //login
    @GetMapping("/login")
    public String login() {

        return "home/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("adminlogin");
        return "redirect:/home";
    }

    //Homepage
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        String value = (String) session.getAttribute("username");
        model.addAttribute("username", value);
        if (!employeeService.checkSession(session)){
            return "redirect:/home";
        }
        return "home/home";
    }

    // Metoden håndterer en POST-anmodning til "/login" -ruten. PostMapping("/login")
    @PostMapping("/login")
    public String loginAccount(String username, String user_password, Model model, HttpSession session) {
        // Finder en medarbejder baseret på brugernavn og adgangskode.
        Employee employee = employeeService.findbyuserandpassword(username,user_password);
        // Gemmer den pågældende medarbejder i sessionen med nøgle "adminlogin".
        session.setAttribute("adminlogin", employee);
        // Udskriver resultatet af sessionens gyldighed til konsollen.
        System.out.println(employeeService.checkSession(session));

        // Hvis medarbejderen eksisterer og er aktiv...
        if (employee != null && employee.getIs_active()==1){
            // Gemmer brugernavnet i sessionen med nøgle "username".
            session.setAttribute("username", username);
            // Omdirigerer brugeren til "/home"-siden.
            return "redirect:/home";
        } else {
            // Tilføjer en fejlbesked til modellen, hvis medarbejderen ikke eksisterer eller ikke er aktiv.
            model.addAttribute("invalid", "bruger findes ikke");
            // Returnerer login-siden.
            return "home/login";

        }
    }
}