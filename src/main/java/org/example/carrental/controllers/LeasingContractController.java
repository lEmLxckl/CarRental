package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.Service.Leasing_contractService;
import org.example.carrental.model.Customer;
import org.example.carrental.model.Leasing_contract;
import org.example.carrental.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.example.carrental.Service.CustomerService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.carrental.Service.VehicleService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Controller
public class LeasingContractController {

    @Autowired
    Leasing_contractService leasing_contractService;
    @Autowired
    VehicleService carService;
    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;

    // Denne metode fanger alle ledige biler og sender dem til createContract-viewet
    @GetMapping("/createContract")
    public String createLeaseContract(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Car> availableCars = carService.getAvailable();
        model.addAttribute("available", availableCars);
        return "createContract";
    }

    // Her vælger den en bil basert på bilens vehicle_number
    // og sender den til carSelected-siden
    @PostMapping("/chooseCar")
    public String chooseCar(Model model, int vehicle_number, HttpSession session, RedirectAttributes redirectAttributes) {
        Car car = carService.findId(vehicle_number);
        if (car == null) {
            redirectAttributes.addFlashAttribute("error", "The car with the given vehicle number could not be found");
            return "redirect:/createContract";
        } else {
            model.addAttribute("update", car);
            model.addAttribute("model", "");
            session.setAttribute("numb", car.getVehicle_number());
            if (car.getFlow() == 1) {
                redirectAttributes.addFlashAttribute("flowError", "The car is already rented out");
                return "redirect:/createContract";
            } else {
                return "carSelected";
            }
        }
    }

    //Her viser den alle lejekontrakterne
    // og den samlede pris for lejekontrakterne
    @GetMapping("/viewLeaseContracts")
    public String viewLeaseContracts(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Leasing_contract> LC = leasing_contractService.fetchAll();
        model.addAttribute("LC", LC);
        double totalPrice = leasing_contractService.calculateTotalPriceOfLeasingContracts();
        model.addAttribute("totalPriceRent", totalPrice);
        return "viewLeaseContracts";
    }

    // her forbereder den data til lease-viewet
    // udfra en valgt bil
    @GetMapping("/lease")
    public String lease(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        String username = (String) session.getAttribute("username");
        Integer numb = (Integer) session.getAttribute("numb");
        List<Customer> customers = customerService.getAllCustomers();
        if (numb != null) {
            Car car = carService.findId(numb);
            model.addAttribute("update", car);
            model.addAttribute("username", username);
            model.addAttribute("customers", customers);
            return "lease";
        } else {
            return "error";
        }
    }

    // Denne metode viser leaseTypes-Siden
    @GetMapping("/leaseTypes")
    public String leaseTypes() {
        return "leaseTypes";
    }

    //Denne metode sætter leasingtypen i session
    // derefter bliver man omdirigeret til createContract-siden
    @GetMapping("/selectLeaseType")
    public String selectLeaseType(HttpSession session, @RequestParam("leaseType") String leaseType) {
        session.setAttribute("leaseType", leaseType);
        return "redirect:/createContract";
    }


    // Denne metode oprette en lejekontrakt
    // på baggrund af det data der bliver tastet ind
    // og så bliver der valideret lejeperiode
    @PostMapping("/createLeasingContract")
    public String createLeasingContract(LocalDate start_date, LocalDate end_date, Model model, HttpSession session, int customer_id, String username, RedirectAttributes redirectAttributes) {
        int numb = (int) session.getAttribute("numb");
        Car car = carService.findId(numb);

        model.addAttribute("update", car);
        model.addAttribute("username", username);
        Period period = Period.between(start_date, end_date);
        int totalDays = period.getDays() + period.getMonths() * 30 + period.getYears() * 365;

        String leaseType = (String) session.getAttribute("leaseType");
        if ("unlimited".equals(leaseType)) {
            if (totalDays < 90) {
                redirectAttributes.addFlashAttribute("error", "The lease period for an unlimited lease must be at least 90 days.");
                return "redirect:/createContract";
            }
        } else if ("limited".equals(leaseType)) {
            if (totalDays < 120 || totalDays > 150) {
                redirectAttributes.addFlashAttribute("error", "The lease period for a limited lease must be between 120 and 150 days.");
                return "redirect:/createContract";
            }
        }

        double monthlyPrice = car.getPrice();
        double totalPrice = (monthlyPrice / 30) * totalDays;
        model.addAttribute("update", car);
        model.addAttribute("username", username);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("startDate", start_date);
        model.addAttribute("endDate", end_date);
        model.addAttribute("price", monthlyPrice);
        model.addAttribute("customerId", customer_id);
        session.setAttribute("totalPriceRent", totalPrice);
        session.setAttribute("startDate", start_date);
        session.setAttribute("endDate", end_date);
        session.setAttribute("customer", customer_id);
        session.setAttribute("username", username);
        Customer customer = customerService.findId(customer_id);
        session.setAttribute("customer_id", customer);
        session.setAttribute("customername", customer.getFull_name());
        return "redirect:/leaseConfirm";
    }



    // Her viser den leaseConfirm-siden
    // Hvori man får alt information for bekræftelse osv
    @GetMapping("/leaseConfirm")
    public String leaseConfirmation(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        String username = (String) session.getAttribute("username");
        String customername = (String) session.getAttribute("customername");
        LocalDate startDate = (LocalDate) session.getAttribute("startDate");
        LocalDate endDate = (LocalDate) session.getAttribute("endDate");
        double totalprice = (double) session.getAttribute("totalPriceRent");
        int customer = (int) session.getAttribute("customer");

        Integer numb = (Integer) session.getAttribute("numb");
        Car car = carService.findId(numb);
        model.addAttribute("update", car);
        model.addAttribute("username", username);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("customer", customer);
        model.addAttribute("totalPriceRent", totalprice);
        model.addAttribute("customername", customername);
        return "leaseConfirm";
    }

    //  bekræfter lejekontrakten
    // samt updates bilens status
    // og derefter tilføjer den kontrakten til databasen
    @PostMapping("/confirmLease")
    public String confirmLease(Model model, HttpSession session, Leasing_contract leasing_contract) {
        String username = (String) session.getAttribute("username");
        LocalDate startDate = (LocalDate) session.getAttribute("startDate");
        String customername = (String) session.getAttribute("customername");
        LocalDate endDate = (LocalDate) session.getAttribute("endDate");
        double totalprice = (double) session.getAttribute("totalPriceRent");
        int customer = (int) session.getAttribute("customer");
        Integer numb = (Integer) session.getAttribute("numb");
        Car car = carService.findId(numb);
        model.addAttribute("update", car);
        model.addAttribute("username", username);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("customer", customer);
        model.addAttribute("totalPriceRent", totalprice);
        model.addAttribute("customername", customername);
        leasing_contractService.addLeasingContract(leasing_contract);
        carService.updateAfterContract(numb);
        return "redirect:/home";
    }
}
