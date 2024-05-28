package org.example.carrental.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Employee;
import org.example.carrental.model.Usertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    EmployeeService employeeService;

    // Start page
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("adminlogin");
        return "redirect:/";
    }

    // Homepage
    @GetMapping("/home")
    public String home(HttpSession session, Model model, Employee employee) {
        String username = (String) session.getAttribute("username");
        Usertype usertype = (Usertype) session.getAttribute("usertype");
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        model.addAttribute("username", username);
        model.addAttribute("usertype", usertype);
        return "home";
    }

    // Handle a POST request to "/login" route
    @PostMapping("/login")
    public String loginAccount(String username, String user_password, Model model, HttpSession session) {
        // Find an employee based on username and password
        Employee employee = employeeService.findByUserAndPassword(username, user_password);
        session.setAttribute("adminlogin", employee);
        System.out.println(employeeService.checkSession(session));

        // If the employee exists and is active...
        if (employee != null && employee.getIs_active() == 1) {
            session.setAttribute("adminlogin", employee);
            session.setAttribute("username", username);
            session.setAttribute("usertype", employee.getUsertype());
            return "redirect:/home";
        } else {
            model.addAttribute("invalid", "User does not exist");
            return "login";
        }
    }

    @GetMapping("/set_session_cookie")
    public String setSessionCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("mySessionCookie", "Hello_Session_Cookie");
        response.addCookie(cookie);
        return "redirect:/get_session_cookie";
    }

    @GetMapping("/get_session_cookie")
    public String getSessionCookie(@CookieValue(name = "mySessionCookie", defaultValue = "N/A") String cookieValue, Model model) {
        model.addAttribute("cookieValue", cookieValue);
        return "show_session_cookie";
    }

    @GetMapping("/delete_session_cookie")
    public String deleteSessionCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("mySessionCookie", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/get_session_cookie";
    }

    @GetMapping("/update_session_cookie")
    public String updateSessionCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("mySessionCookie", "Updated_information");
        response.addCookie(cookie);
        return "redirect:/get_session_cookie";
    }

    @GetMapping("/write_cookie")
    public String writeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", "admin");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
        System.out.printf("Cookie was set: %s = %s%n", cookie.getName(), cookie.getValue());
        return "redirect:/";
    }

    @GetMapping("/read_cookie")
    public String readCookie(@CookieValue(name = "MyCookie", defaultValue = "N/A") String myCookie) {
        System.out.printf("Cookie was read: %s = %s%n", "MyCookie", myCookie);
        return "index";
    }
}
