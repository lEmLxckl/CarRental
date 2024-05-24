package org.example.carrental.controllers;
import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Customer;
import org.example.carrental.model.Leasing_contract;
import org.example.carrental.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.example.carrental.service.CustomerService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.carrental.service.Leasing_contractService;
import java.time.LocalDate;
import org.example.carrental.service.VehicleService;
import java.time.Period;
import java.util.List;

@Controller
public class LeasingContractController {


    int number;
    @Autowired
    Leasing_contractService leasing_contractService;
    @Autowired
    VehicleService carService;
    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    //metoden for oprettelsen af en lejekontrakt de ledige biler vises.
    @GetMapping("/opretKontrakt")
    public String leasingKontrakt(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        List<Car> availableCars = carService.fetchAvailable();
        model.addAttribute("available", availableCars);

        return "opretKontrakt";
    }

    //viderefortsættelse af øvre metode hvor der bliver inputvaliderde.

    @PostMapping("/chooseCar")
    public String seBiler(Model model, int vehicle_number, HttpSession session, RedirectAttributes redirectAttributes) {
        Car car = carService.findId(vehicle_number);
        if (car == null) {
            redirectAttributes.addFlashAttribute("error", "Bilen med det angivne vognnummer kunne ikke findes");
            return "redirect:/opretKontrakt";
        } else {
            model.addAttribute("opdater", car);
            model.addAttribute("model", "");
            session.setAttribute("numb", car.getVehicle_number());
            if (car.getFlow() == 1) {
                redirectAttributes.addFlashAttribute("flowerror", "Bilen er allerede lejet ud");
                return "redirect:/opretKontrakt";
            } else {
                return "bilValgt";
            }
        }
    }

    //metode hvor der bliver vist en bekræftelsesmenu
    @GetMapping("/selejekontrakt")
    public String Leasing_contracts(Model model, HttpSession session){
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        List<Leasing_contract> LC = leasing_contractService.fetchAll();
        model.addAttribute("LC",LC );
        System.out.println(LC.size());
        double totalPrice = leasing_contractService.calculateTotalPriceOfLeasingContracts();
        model.addAttribute("totalPriceRent", totalPrice);
        return "seLejekontrakt";
    }


    //metode hvor der bliver sammlet nogle oplysninger, fra medarbejderen og bilen.
    @GetMapping("/lej")
    public String leasing(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        String username = (String) session.getAttribute("username");
        Integer numb = (Integer) session.getAttribute("numb");
        List<Customer> customers = customerService.fetchAll();
        if (numb != null) {
            Car car = carService.findId(numb);
            model.addAttribute("opdater", car);
            model.addAttribute("username", username);
            model.addAttribute("customers", customers);
            return "lej";
        } else {
            // handle case where numb is null
            return "error";
        }
    }


    //fortsættelse af metoden ovenfra hvor der herunder bliver inputvalideret i flere punkter.Såsom antal månender. totalprisen beregnes også
    @PostMapping("/createLeasingContract")
    public String createLease(LocalDate start_date, LocalDate end_date, Model model, HttpSession session, int customer_id, String username, RedirectAttributes redirectAttributes) {
        int numb = (int) session.getAttribute("numb");
        Car car = carService.findId(numb); // sekvens step 1.1.1

        model.addAttribute("opdater", car);
        model.addAttribute("username", username);
        Period period = Period.between(start_date, end_date);
        int months = period.getMonths();
        int days = period.getDays();
        // check if lease period is at least 3 months
        if (months < 3) {
            redirectAttributes.addFlashAttribute("error", "Lej perioden skal være mindst 3 måneder.");
            return "redirect:/lej";
        } else {
            // Calculate leasing price
            double monthlyPrice = car.getPrice();
            double totalPrice = monthlyPrice * months;
            if (days > 0) {
                double dailyPrice = (double) monthlyPrice / 30;
                double extraDaysPrice = dailyPrice * days;
                totalPrice += Math.round(extraDaysPrice);
            }
            System.out.println(totalPrice);
            model.addAttribute("opdater", car);
            model.addAttribute("username", username);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("startDate", start_date);
            model.addAttribute("endDate", end_date);
            model.addAttribute("price", monthlyPrice);
            model.addAttribute("customerId", customer_id);
            model.addAttribute("totalPriceRent", totalPrice);
            session.setAttribute("totalPriceRent", totalPrice);
            session.setAttribute("startDate", start_date);
            session.setAttribute("endDate", end_date);
            session.setAttribute("customer", customer_id);
            session.setAttribute("username", username);
            Customer customer = customerService.findId(customer_id); // sekvens 1.2 og efter
            session.setAttribute("customer_id", customer);
            session.setAttribute("customername", customer.getFull_name());

            return "redirect:/leaseconfirm";
        }
    }

    //metode der samler oplysningerne fra tidligere metode og viser en kvittering
    @GetMapping("/leaseconfirm")
    public String leasingConfirmation(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)){
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
        model.addAttribute("opdater", car);
        model.addAttribute("username", username);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("customer", customer);
        model.addAttribute("totalPriceRent", totalprice);
        model.addAttribute("customername", customername);
        return "leaseconfirm";
    }

    //videreførelse af tidligere metode, hvor man bliver vist en html side, og man kan trykke på confirm for at færdiggøre opretning
    @PostMapping("/createLeasingContractConfirmed")
    public String leasingAdd(Model model, HttpSession session, Leasing_contract leasing_contract) {
        String username = (String) session.getAttribute("username");
        LocalDate startDate = (LocalDate) session.getAttribute("startDate");
        String customername = (String) session.getAttribute("customername");
        LocalDate endDate = (LocalDate) session.getAttribute("endDate");
        double totalprice = (double) session.getAttribute("totalPriceRent");
        int customer = (int) session.getAttribute("customer");
        Integer numb = (Integer) session.getAttribute("numb");
        Car car = carService.findId(numb);
        model.addAttribute("opdater", car);
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