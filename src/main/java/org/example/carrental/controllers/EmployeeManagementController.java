package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Employee;
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

    /* Denne metode bruges til at håndtere en GET-anmodning til "/personale" -ruten,
     og den returnerer en visningsside kaldet "personale", der viser alle medarbejdere.
    */
    @GetMapping("/personale")
    public String getAllEmployees(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)){

            /* Før visningen kontrolleres sessionen for at sikre, at den er gyldig.
      Hvis sessionen ikke er gyldig, omdirigeres brugeren til startsiden ("/").*/
            return "redirect:/";
        }
        List<Employee> employees = employeeService.fetchAllEmployees();
        Employee adminLogin = (Employee) session.getAttribute("adminlogin");

        model.addAttribute("admin", adminLogin);

        /*     Derudover tilføjes en administratorkonto og en liste med medarbejdere til modellen,
       som bruges til at opdatere visningen.*/
        model.addAttribute("employees", employees);

        return "home/personale";
    }

    @GetMapping("/opretPersonale")
    public String opretPersonale(HttpSession session) {
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        String adminLogin = (String) session.getAttribute("username");
        Employee adminEmployee = employeeService.findAdminUser(adminLogin);
        if (adminEmployee == null) {
            return "redirect:/personale";
        } else {

            return "home/opretPersonale";
        }

    }

    @PostMapping("/opretPersonaler")
    public String opretPersonaler(Employee employee, Model model, HttpSession session) {
        employeeService.createEmployee(employee);
        return "redirect:/personale";

    }

    @GetMapping("/personale/{username}")
    public String fireEmployee(@PathVariable("username") String username,  HttpSession session){
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        String adminLogin = (String) session.getAttribute("username");
        Employee adminEmployee =employeeService.findAdminUser(adminLogin);
        if (adminEmployee==null) {
            return"redirect:/personale";
        } else {
            employeeService.fireEmployee(username);
            return "redirect:/personale";
        }

    }

    @GetMapping("/opdaterPersonale/{username}")
    public String findByUsername(@PathVariable("username") String username, Model model, HttpSession session) {
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        Employee employee = employeeService.findByUsername(username);
        model.addAttribute("employee", employee);
        session.setAttribute("urlusername", employee.getUsername());
        return "home/opdaterPersonale";
    }



    //opdaterer personale oplysninger

    @PostMapping("/opdateretPersonale")
    public String opdateretPersonal(Employee employee, int is_active, int is_admin,
                                    HttpSession session, RedirectAttributes redirectAttributes) {
        String usernames = (String) session.getAttribute("urlusername");

        //hvis begge to felter ikke har rigtig inputs, skal der gives to fejl meddelelser,
        if(is_active != 0 && is_active != 1 && is_admin != 0 && is_admin != 1  ){
            redirectAttributes.addFlashAttribute("fejl", "Admin value should be 0 or 1");
            redirectAttributes.addFlashAttribute("fejl2", "Active value should be 0 or 1");
            return "redirect:/opdaterPersonale/" + usernames;
        }
        //hvis active felten ikke er korrect skal der gives en meddelse til dette
        else if (is_active != 0 && is_active != 1) {
            redirectAttributes.addFlashAttribute("fejl", "Active value should be 0 or 1");
            return "redirect:/opdaterPersonale/" + usernames;
        }
        //hvis active felten ikke er korrect skal der gives en meddelse til dette
        else if (is_admin != 0 && is_admin != 1) {
            redirectAttributes.addFlashAttribute("fejl2", "Admin value should be 0 or 1");
            return "redirect:/opdaterPersonale/" + usernames;
        }else {
            //hvis begge inputs er indtastet rigtigt skal update væres succesfuld og den skal refresh personale html siden

            employeeService.updateEmployee(employee);
            return "redirect:/personale";
        }
    }
}