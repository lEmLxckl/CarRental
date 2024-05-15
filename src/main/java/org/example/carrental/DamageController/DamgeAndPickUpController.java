package org.example.carrental.DamageController;

import org.example.carrental.DamageService.DamageAndPickUpService;
import org.example.carrental.model.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DamgeAndPickUpController {
    @Autowired
    DamageAndPickUpService service;

    @GetMapping("/damageAndPickUp")
    public String visSkadeOgUdbedringForm(Model model) {
        model.addAttribute("damageAndPickUp", new DamgeAndPickUpController());
        return "home/damageAndPickUp";
    }

    @PostMapping("/saveDamageAndPickUp")
    public String save(DamageReport damageReport) {
        service.saveDamageAndPickUp(damageReport);
        return "redirect:/damageAndPickUp";
    }
}

