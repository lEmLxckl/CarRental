package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.DamageAndPickUpService;
import org.example.carrental.model.DamageReport;
import org.example.carrental.model.Employee;
import org.example.carrental.model.Usertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DamgeAndPickUpController {

    @Autowired
    private DamageAndPickUpService service;

    @GetMapping("/damageReport")
    public String damageReport(Model model, HttpSession session, Employee employee) {
    employee = (Employee) session.getAttribute("employee");
        if (employee != null && (employee.getUsertype() == Usertype.DAMAGEREPORTER || employee.getUsertype() == Usertype.ADMIN)) {
            return "home/damageReport";
        }
        return "home/damageReport";
    }

    @GetMapping("/damageAndPickUp")
    public String getDamageAndPickUp(Model model, HttpSession session, Employee employee) {
         employee = (Employee) session.getAttribute("employee");
        if (employee != null && (employee.getUsertype() == Usertype.DAMAGEREPORTER || employee.getUsertype() == Usertype.ADMIN)) {
            model.addAttribute("damageReport", new DamageReport());
            return "home/damageAndPickUp";
        } else {
            model.addAttribute("accessDenied", "You don't have permission to access this page.");
            return "redirect:/menu";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id, HttpSession session, Model model) {
        Employee requestingEmployee = (Employee) session.getAttribute("employee");
        if (requestingEmployee != null && requestingEmployee.getUsertype() == Usertype.ADMIN || requestingEmployee.getUsertype() == Usertype.DAMAGEREPORTER) {
            model.addAttribute("accessDenied", "You don't have permission to access this page.");

            service.deleteDamageAndPickUp(id);
            return "redirect:/menu";
        }
        return "redirect:/menu";
    }

    @PostMapping("/saveDamageAndPickUp")
    public String save(@ModelAttribute DamageReport damageReport) {
        service.saveDamageAndPickUp(damageReport);
        return "redirect:/showAll";
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        List<DamageReport> damageReports = service.showAllDamageAndPickUps();
        model.addAttribute("damageReports", damageReports);
        return "home/showAll";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model, HttpSession session) {
        Employee requestingEmployee = (Employee) session.getAttribute("employee");
        if (requestingEmployee != null && requestingEmployee.getUsertype() == Usertype.ADMIN || requestingEmployee.getUsertype() == Usertype.DAMAGEREPORTER) {
            model.addAttribute("accessDenied", "You don't have permission to access this page.");

            DamageReport damageReport = service.getDamageAndPickUp(id);
            model.addAttribute("damageReport", damageReport);
            return "home/updateDamageReport";
        }
        return "redirect:/showAll";
    }

    @PostMapping("/updateDamageAndPickUp")
    public String updateDamageAndPickUp(@ModelAttribute DamageReport damageReport) {
        service.updateDamageAndPickUp(damageReport);
        return "redirect:/showAll";
    }


}