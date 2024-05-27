package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.*;
import org.example.carrental.Service.Damage_reportService;
import org.example.carrental.Service.Leasing_contractService;
import org.example.carrental.model.Damage_category;
import org.example.carrental.model.Damage_report;
import org.example.carrental.model.Leasing_contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
class DamageAndPickUpController {

    @Autowired
    Damage_reportService damageReportService;
    @Autowired
    DamageService damageService;
    @Autowired
    Leasing_contractService leasingContractService;
    @Autowired
    VehicleService carService;
    @Autowired
    EmployeeService employeeService;

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

    // Return list of leasing contracts joined with car where flow is 1
    @GetMapping("/createDamageReportSelect")
    public String createDamageReportSelect(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Leasing_contract> leasingContracts = leasingContractService.fetchFlow1();
        model.addAttribute("contract", leasingContracts);
        return "createDamageReportSelect";
    }

    // Return list of leasing contracts joined with car where flow is 1, and contract id must be selected
    @PostMapping("/createDamageReport")
    public String createDamageReport(Model model, HttpSession session, Integer contract_id, RedirectAttributes redirectAttributes) {
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

        List<Damage_category> damageCategories = damageService.getAllDamageCategories();
        model.addAttribute("category", damageCategories);
        model.addAttribute("contractId", contractId);
        session.setAttribute("contractId", contractId);
        return "createDamageReport";
    }

    // Select damage and calculate total price for damage report
    @PostMapping("/addDamageReport")
    public String addDamageReport(Model model, Integer category_id, RedirectAttributes redirectAttributes, Integer finish, HttpSession session, Damage_report damageReport) {
        Double totalPrice = (Double) session.getAttribute("totalPrice");
        if (totalPrice == null) {
            totalPrice = 0.0;
        }
        if (category_id == null && finish == 1) {
            return "redirect:/damageReportReceipt";
        }

        Double value = damageService.getDamagePrice(category_id);
        totalPrice += value.doubleValue();

        session.setAttribute("totalPrice", totalPrice);

        if (finish == null) {
            redirectAttributes.addFlashAttribute("totalPrice", totalPrice);
            return "redirect:/createDamageReport";
        } else {
            // The finish condition is met, you can redirect or perform any other action here
            return "redirect:/damageReportReceipt";
        }
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
    public String confirmDamageReport(Model model, RedirectAttributes redirectAttributes, HttpSession session, Damage_report damageReport) {
        Leasing_contract leasingContract = (Leasing_contract) session.getAttribute("leasingContract");
        damageReportService.addDamage_report(damageReport);
        carService.updateAfterDamageReport(leasingContract.getVehicle_number());
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
    public String updateReport(Damage_report damageReport, int report_id) {
        damageReportService.updateReport(damageReport, report_id);
        return "redirect:/damageReport";
    }

    // Delete the damage report
    @GetMapping("/deleteDamageReport/{report_id}")
    public String deleteDamageReport(@PathVariable("report_id") int report_id, HttpSession session) {
        boolean deleted = damageReportService.deleteReport(report_id);
        if (deleted) {
            return "redirect:/damageReport";
        } else {
            return "redirect:/damageReport";
        }
    }
}
