package org.example.carrental.controllers;

import org.example.carrental.model.LeaseType;
import org.example.carrental.service.UserTypeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class UserTypeDataController {

    @Autowired
    private UserTypeDataService userTypeDataService; // Inject an instance of UserTypeDataService

    @GetMapping("/createLeaseForm")
    public String showCreateLeaseForm() {
        return "home/lease_form";
    }

    @PostMapping("/createLease")
    public String createLease(
            @RequestParam int customerId,
            @RequestParam int vehicleId,
            @RequestParam LeaseType typeOfLease,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        userTypeDataService.createLease(customerId, vehicleId, typeOfLease, startDate, endDate);
        return "redirect:/";
    }
}
