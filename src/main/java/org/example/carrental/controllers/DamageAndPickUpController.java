package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.DamageAndPickUpService;
import org.example.carrental.model.DamageReport;
import org.example.carrental.model.Employee;
import org.example.carrental.model.Usertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DamageAndPickUpController {

        private final DamageAndPickUpService service;

        @Autowired
        DamageAndPickUpController(DamageAndPickUpService service) {
            this.service = service;
        }

        @GetMapping("/damageAndPickUp")
        public String getDamageAndPickUp(Model model, HttpSession session) {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee != null && (employee.getUsertype() == Usertype.DAMAGEREPORTER || employee.getUsertype() == Usertype.ADMIN)) {
                model.addAttribute("damageReport", new DamageReport());
                return "home/damageAndPickUp";
            } else {
                model.addAttribute("accessDenied", "You don't have permission to access this page.");
                return "redirect:/dashboard";
            }
        }


        @PostMapping("/saveDamageAndPickUp")
        public String save(@ModelAttribute DamageReport damageReport) {
            service.saveDamageAndPickUp(damageReport);
            return "redirect:/";

        }
    }
