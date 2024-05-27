package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.DamageService;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Damage_category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DamageController {

    @Autowired
    DamageService damageService;

    @Autowired
    EmployeeService employeeService;

    // Method to fetch damage categories
    @GetMapping("/viewDamagePrices")
    public String viewDamagePrices(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Damage_category> damageCategories = damageService.getAllDamageCategories();
        model.addAttribute("category", damageCategories);
        return "viewDamagesPrices";
    }

    // Method to add a new damage
    @GetMapping("/addDamage")
    public String addDamage(HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        return "addDamage";
    }

    // Method to add a new damage, continuation
    @PostMapping("/createNewDamage")
    public String addDamageToList(@ModelAttribute Damage_category d, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        damageService.addDamage(d);
        return "redirect:/viewDamagePrices";
    }

    // Method to update a category, fetching the id first
    @GetMapping("/updateDamage/{category_id}")
    public String updateDamage(@PathVariable("category_id") int category_id, Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Damage_category damage = damageService.findExactDamage(category_id);
        model.addAttribute("update", damage);
        return "updateDamage";
    }

    // Continuation of the update method, updating the category details
    @PostMapping("/damageUpdate")
    public String updateDamageToList(@ModelAttribute Damage_category damageCategory, int category_id) {
        damageService.updateCategory(damageCategory, category_id);
        return "redirect:/viewDamagePrices";
    }

    // Method to delete a category
    @GetMapping("/deleteDamage/{category_id}")
    public String deleteDamage(@PathVariable("category_id") int category_id, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        boolean deleted = damageService.deleteDamage(category_id);
        return "redirect:/viewDamagePrices";
    }
}
