package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.DamageService;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Damage_category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class DamageController {

    @Autowired
    DamageService damageService;

    @Autowired
    EmployeeService employeeService;

    // metode som hiver fat i damagekategorier
    @GetMapping("/skader")
    public String skaddeRapportListe(Model model, HttpSession session){
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        List<Damage_category> damage_category =  damageService.fetchAllDamageCategories();
        model.addAttribute("category", damage_category);

        return "skader";
    }

    // metode som anvendes til at tilføje en ekstra skade
    @GetMapping("/tilføjSkade")
    public String addDamage(HttpSession session) {
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        return "tilføjSkade";
    }
    // en fortsættelse og af overnævnte tilføje metode, hvor en html login forum displays
    @PostMapping("/createNewDamage")
    public String addDamagetoList( Damage_category d, HttpSession session) {
        damageService.addDamage(d);
        return "redirect:/skader";
    }

    //metode som opdaterer en kategori, hvor vi først tager fat i id'en.
    @GetMapping("/updateOneDamage/{category_id}")
    public String updateDamage(@PathVariable("category_id") int category_id, Model model, HttpSession session) {
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        Damage_category damage = damageService.findSpecifikDamage(category_id);
        model.addAttribute("opdater", damage);
        return "opdaterSkade";
    }

    //fortsættelse af overstående metode, hvor man ajourføre oplysningerne af den tildelte kategori.
    @PostMapping("/damageUpdate")
    public String updateDamageToList(Damage_category damage_category, int category_id) {
        damageService.updateCategory(damage_category, category_id);
        return "redirect:/skader";
    }

    //metode til at slette en kategori i tilfælde af overflødighedhed eller lignende
    @GetMapping("/deleteOneDamage/{category_id}")
    public String deleteOne(@PathVariable("category_id") int category_id, HttpSession session){
        boolean deleted = damageService.deleteDamage(category_id);
        if (deleted){
            return "redirect:/skader";
        }else {
            return "redirect:/skader";
        }
    }



}

