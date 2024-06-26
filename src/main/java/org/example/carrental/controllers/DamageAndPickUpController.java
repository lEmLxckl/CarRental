package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.*;
import org.example.carrental.model.Damage_category;
import org.example.carrental.model.Damage_report;
import org.example.carrental.model.Leasing_contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DamageAndPickUpController {

    @Autowired
    private Damage_reportService damageReportService;

    @Autowired
    private DamageService damageService;

    @Autowired
    private Leasing_contractService leasingContractService;

    @Autowired
    private VehicleService carService;

    @Autowired
    private EmployeeService employeeService;

    // Return list of damage reports
    @GetMapping("/damageReport")
    public String showDamageReport(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Damage_report> damageReports = damageReportService.getAllReports();
        model.addAttribute("damageReport", damageReports);
        return "damageReport";
    }

    // Return list of leasing contracts joined with car where flow is 1, and contract id must be selected
    @PostMapping("/createDamageReport")
    public String createDamageReport(Model model, HttpSession session, @RequestParam Integer contract_id, RedirectAttributes redirectAttributes) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }

        Leasing_contract leasingContract = leasingContractService.findIdAndFlow(contract_id);

        if (leasingContract == null) {
            redirectAttributes.addFlashAttribute("error", "Please select one of the contracts below");
            return "redirect:/createDamageReportSelect";
        }
        session.setAttribute("contract", leasingContract.getContract_id());
        session.setAttribute("leasingContract", leasingContract);
        return "redirect:/createDamageReport";
    }

    // Return list of damages
    @GetMapping("/createDamageReport")
    public String showDamageReportForm(HttpSession session, Model model) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Integer contractId = (Integer) session.getAttribute("contract");
        Double totalPrice = (Double) session.getAttribute("totalPrice");

        List<Damage_category> damageCategories = damageService.getAllDamageCategories();
        model.addAttribute("category", damageCategories);
        model.addAttribute("contractId", contractId);
        model.addAttribute("totalPrice", totalPrice); // Ensure this is added
        session.setAttribute("contractId", contractId);
        return "createDamageReport";
    }

    // Return list of leasing contracts joined with car where flow is 1
   /* @GetMapping("/createDamageReportSelect")
    public String createDamageReportSelect(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Leasing_contract> leasingContracts = leasingContractService.fetchFlow1();
        model.addAttribute("contract", leasingContracts);
        return "createDamageReportSelect";
    }*/

    @GetMapping("/createDamageReportSelect")
    public String createDamageReportSelect(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Leasing_contract> leasingContracts = leasingContractService.fetchFlow1();
        model.addAttribute("contract", leasingContracts);
        // Check for error message in flash attributes
        String errorMessage = (String) session.getAttribute("error");
        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            session.removeAttribute("error"); // Remove error after displaying it once
        }
        return "createDamageReportSelect";
    }
    // Select damage and calculate total price for damage report
    @PostMapping("/addDamageReport")
    public String addDamageReport(Model model, @RequestParam(required = false) Integer category_id, @RequestParam(required = false) Integer finish, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }

        Double totalPrice = (Double) session.getAttribute("totalPrice");
        if (totalPrice == null) {
            totalPrice = 0.0;
        }
        if (category_id == null && finish != null && finish == 1) {
            return "redirect:/damageReportReceipt";
        }

        if (category_id != null) {
            Double value = damageService.getDamagePrice(category_id);
            if (value != null) {
                totalPrice += value;
                session.setAttribute("totalPrice", totalPrice);
            }
        }

        if (finish == null) {
            redirectAttributes.addFlashAttribute("totalPrice", totalPrice);
            return "redirect:/createDamageReport";
        } else {
            return "redirect:/damageReportReceipt";
        }
    }

    // Handle the add damage form submission
    @PostMapping("/createNewDamage")
    public String addDamageToList(@ModelAttribute Damage_category damageCategory, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        System.out.println("Received Damage Category: " + damageCategory); // Logging for debugging
        damageService.addDamage(damageCategory);
        return "redirect:/viewDamagePrices";
    }

    // Fetch receipt of our previous inputs
    @GetMapping("/damageReportReceipt")
    public String showDamageReportReceipt(HttpSession session, Model model) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Double totalPrice = (Double) session.getAttribute("totalPrice");
        Integer contractId = (Integer) session.getAttribute("contract");
        String username = (String) session.getAttribute("username");
        model.addAttribute("contractId", contractId);
        model.addAttribute("username", username);
        model.addAttribute("totalPrice", totalPrice);
        return "damageReportReceipt";
    }

    // Confirm your receipt and add your damage report
    @PostMapping("/confirmReceipt")
    public String confirmDamageReport(HttpSession session, @RequestParam("contract_id") int contractId,
                                      @RequestParam("total_price") double totalPrice,
                                      @RequestParam("username") String username) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }

        Leasing_contract leasingContract = (Leasing_contract) session.getAttribute("leasingContract");
        if (leasingContract != null) {
            Damage_report damageReport = new Damage_report();
            damageReport.setContract_id(contractId);
            damageReport.setTotal_price(totalPrice); // Set total price
            damageReport.setUsername(username);
            damageReportService.addDamage_report(damageReport);
            carService.updateAfterDamageReport(leasingContract.getVehicle_number());
        }
        return "redirect:/damageReport";
    }

    // Update damage report buttons
    @GetMapping("/updateDamageReport/{report_id}")
    public String updateDamageReport(@PathVariable("report_id") int report_id, Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Damage_report damageReport = damageReportService.findExactReport(report_id);
        model.addAttribute("update", damageReport);
        return "updateDamageReport";
    }

    // Update the damage report
    @PostMapping("/updateDamageReport")
    public String updateReport(@ModelAttribute Damage_report damageReport) {
        damageReportService.updateReport(damageReport, damageReport.getReport_id());
        return "redirect:/damageReport";
    }

    // Delete the damage report
    @GetMapping("/deleteDamageReport/{report_id}")
    public String deleteDamageReport(@PathVariable("report_id") int report_id, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        boolean deleted = damageReportService.deleteReport(report_id);
        return "redirect:/damageReport";
    }
}
