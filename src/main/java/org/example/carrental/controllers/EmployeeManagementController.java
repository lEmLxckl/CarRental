package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Employee;
import org.example.carrental.model.Usertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeManagementController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/staff")
    public String getAllEmployees(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Employee> employees = employeeService.getAllEmployees();
        Employee adminLogin = (Employee) session.getAttribute("adminlogin");
        model.addAttribute("admin", adminLogin);
        model.addAttribute("employees", employees);
        return "staff";
    }

    @GetMapping("/createStaff")
    public String createStaff(HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        String adminLogin = (String) session.getAttribute("username");
        Employee adminEmployee = employeeService.findAdminUser(adminLogin);
        if (adminEmployee == null) {
            return "redirect:/staff";
        } else {
            return "createStaff";
        }
    }

    @PostMapping("/createStaff")
    public String createNewStaff(@RequestParam String username,
                                 @RequestParam String user_password,
                                 @RequestParam String full_name,
                                 @RequestParam String email,
                                 @RequestParam String phone,
                                 @RequestParam String usertype,
                                 HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }

        Employee employee = new Employee();
        employee.setUsername(username);
        employee.setUser_password(user_password);
        employee.setFull_name(full_name);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setIs_active(1);
        employee.setIs_admin(0);
        employee.setUsertype(Usertype.fromString(usertype));

        employeeService.createEmployee(employee);
        return "redirect:/staff";
    }

    @GetMapping("/staff/{username}")
    public String deactivateEmployee(@PathVariable("username") String username, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        String adminLogin = (String) session.getAttribute("username");
        Employee adminEmployee = employeeService.findAdminUser(adminLogin);
        if (adminEmployee == null) {
            return "redirect:/staff";
        } else {
            employeeService.deleteEmployee(username);
            return "redirect:/staff";
        }
    }

    @GetMapping("/updateStaff/{username}")
    public String findEmployeeByUsername(@PathVariable("username") String username, Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Employee employee = employeeService.findByUsername(username);
        model.addAttribute("employee", employee);
        session.setAttribute("urlUsername", employee.getUsername());
        return "updateStaff";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@RequestParam String username,
                                 @RequestParam String user_password,
                                 @RequestParam String full_name,
                                 @RequestParam String email,
                                 @RequestParam String phone,
                                 @RequestParam int is_active,
                                 @RequestParam int is_admin,
                                 @RequestParam String usertype,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }

        Employee employee = employeeService.findByUsername(username);
        if (employee == null) {
            return "redirect:/staff";
        }

        if (is_active != 0 && is_active != 1) {
            redirectAttributes.addFlashAttribute("error", "Active value should be 0 or 1");
            return "redirect:/updateStaff/" + username;
        }

        if (is_admin != 0 && is_admin != 1) {
            redirectAttributes.addFlashAttribute("error2", "Admin value should be 0 or 1");
            return "redirect:/updateStaff/" + username;
        }

        employee.setUser_password(user_password);
        employee.setFull_name(full_name);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setIs_active(is_active);
        employee.setIs_admin(is_admin);
        employee.setUsertype(Usertype.fromString(usertype));

        employeeService.updateEmployee(employee);
        return "redirect:/staff";
    }
}
