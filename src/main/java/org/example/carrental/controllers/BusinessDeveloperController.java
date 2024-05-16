package org.example.carrental.controllers;


import org.example.carrental.Service.BusinessDeveloperService;
import org.example.carrental.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BusinessDeveloperController {

    @Autowired
    private BusinessDeveloperService businessDeveloperService;

    @GetMapping("/overview")
    public String getOverview(Model model) {

        // Henter vehicles fra service
        List<Vehicle> vehicles = businessDeveloperService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "overview";
    }

    @GetMapping("/overview/price")
    public String sortByPrice(Model model) {

        // Henter vehicles sorteret med pris fra service
        List<Vehicle> vehicles = businessDeveloperService.getAllVehiclesSortedByPrice();
        model.addAttribute("vehicles", vehicles);
        return "overview";
    }

    @GetMapping("/overview/leased")
    public String showLeasedVehicles(Model model) {
        // Henter leaset vehicles fra service
        List<Vehicle> vehicles = businessDeveloperService.getLeasedVehicles();
        model.addAttribute("vehicles", vehicles);
        return "overview";
    }

    @GetMapping("/overview/model")
    public String sortByModel(Model model) {
        // Henter vehicles sorteret efter model fra service
        List<Vehicle> vehicles = businessDeveloperService.getAllVehiclesSortedByModel();
        model.addAttribute("vehicles", vehicles);
        return "overview";

    }
}