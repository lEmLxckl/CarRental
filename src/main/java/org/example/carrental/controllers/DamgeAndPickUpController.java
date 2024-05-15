package org.example.carrental.controllers;

import org.example.carrental.Service.DamageAndPickUpService;
import org.example.carrental.model.Customer;
import org.example.carrental.model.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DamgeAndPickUpController {
        @Autowired
        DamageAndPickUpService service;

        @GetMapping("/damageAndPickUp")
        public String getDamageandPickUp(Model model) {
            model.addAttribute("damageReport", new DamageReport());
            return "home/damageAndPickUp";
        }


        @PostMapping("/saveDamageAndPickUp")
        public String save(@ModelAttribute DamageReport damageReport) {
            service.saveDamageAndPickUp(damageReport);
            return "redirect:/";

        }
    }
