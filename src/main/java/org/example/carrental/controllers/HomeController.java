package org.example.carrental.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    EmployeeService employeeService;

    //Start page
    @GetMapping("/")
    public String index() {
        return "index";
    }

    //login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //Homepage
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        String value = (String) session.getAttribute("username");
        model.addAttribute("username", value);
        if (!employeeService.checkSession(session)){
            return "redirect:/home";
        }
        return "home";
    }

    // Handle login request
    @PostMapping("/login")
    public String login(String username, String user_password, Model model, HttpSession session, HttpServletResponse response) {
        // Find employee based on username and password
        Employee employee = employeeService.findbyuserandpassword(username,user_password);
        // Save the employee in the session with key "adminlogin"
        session.setAttribute("adminlogin", employee);

        // Check if employee exists and is active
        if (employee != null && employee.getIs_active()==1){
            // Save the username in the session with key "username"
            session.setAttribute("username", username);

            // Create a cookie to remember the user for 7 days
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            cookie.setPath("/");
            response.addCookie(cookie);

            // Redirect user to the home page
            return "redirect:/home";
        } else {
            // Add an error message to the model if the employee doesn't exist or is not active
            model.addAttribute("invalid", "User not found");
            // Return login page
            return "login";
        }
    }

    //logout
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        // Invalidate the session
        session.invalidate();

        // Remove the cookie
        Cookie cookie = new Cookie("username", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Redirect to login page
        return "redirect:/";
    }
}