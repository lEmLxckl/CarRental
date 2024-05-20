package org.example.carrental.controllers;

import org.example.carrental.Service.DamageAndPickUpService;
import org.example.carrental.model.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DamgeAndPickUpController {

    @Autowired
    private DamageAndPickUpService service;

    @GetMapping("/damageAndPickUp")
    public String getDamageAndPickUp(Model model) {
        model.addAttribute("damageReport", new DamageReport());
        return "home/damageAndPickUp";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        service.deleteDamageAndPickUp(id);
        return "redirect:/showAll";
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
    public String update(@RequestParam("id") int id, Model model) {
        DamageReport damageReport = service.getDamageAndPickUp(id);
        model.addAttribute("damageReport", damageReport);
        return "home/updateDamageReport";
    }

    @PostMapping("/updateDamageAndPickUp")
    public String updateDamageAndPickUp(@ModelAttribute DamageReport damageReport) {
        service.updateDamageAndPickUp(damageReport);
        return "redirect:/showAll";
    }
}