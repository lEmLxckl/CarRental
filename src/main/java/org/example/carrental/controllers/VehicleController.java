package org.example.carrental.controllers;
import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Employee;
import org.example.carrental.model.Usertype;
import org.example.carrental.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.example.carrental.service.VehicleService;
import java.util.List;
import java.util.Map;
import org.example.carrental.service.LeaseService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VehicleController {

    @Autowired
    VehicleService vehicleService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    LeaseService leaseService;


    // Se liste over alle biler
    @GetMapping("/seallebiler")
    public String car(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Vehicle> cars = vehicleService.fetchAll();
        model.addAttribute("cars", cars);
        return "seallebiler";
    }


    // Se liste over alle biler
    @GetMapping("/ledigbiler")
    public String getAvailableCars(Model model, HttpSession session) {

        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Vehicle> availableCars = vehicleService.fetchAvailable();
        model.addAttribute("available", availableCars);
        return "ledigbiler";
    }

    // Metoden sender dig til en side hvor du kan tilføje en bil
    @GetMapping("/tilføjBiler")
    public String addCar(HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        return "tilføjBiler";
    }

    // Her kan du udfylde informationer om en bil og den bliver gemt i listen i databasen
    @PostMapping("/createNew")
    public String addCartoList(Vehicle vehicle, HttpSession session) {
        vehicleService.addVehicle(vehicle);
        return "redirect:/seallebiler";
    }


    // Her slettes en bil baseret på vehicle number, og der bliver omdigeret til en opdateret liste af biler
    @GetMapping("/deleteOne/{vehicle_number}")
    public String deleteOne(@PathVariable("vehicle_number") int vehicle_number, HttpSession session) {
        boolean deleted = vehicleService.deleteVehicle(vehicle_number);
        if (deleted) {
            return "redirect:/seallebiler";
        } else {
            return "redirect:/seallebiler";
        }
    }

    //Du bliver sendt til siden hvor du kan opdater oplysninger på en bil
    @GetMapping("/opdaterBilen/{vehicle_number}")
    public String updateCar(@PathVariable("vehicle_number") int vehicle_number, Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Vehicle vehicle = vehicleService.findVehicleById(vehicle_number);
        model.addAttribute("opdater", vehicle);
        return "opdaterBil";
    }

    // Denne metode håndtere selve oplysningerne af en bil, når formularen bliver sendt
    @PostMapping("/carupdate")
    public String updateCarToList(Vehicle vehicle, int vehicle_number) {
        vehicleService.updateVehicle(vehicle, vehicle_number);
        return "redirect:/seallebiler";
    }


    @GetMapping("/sammenlagtpris")
    public String getTotalPrice(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Employee adminLogin = (Employee) session.getAttribute(String.valueOf(Usertype.ADMIN));
        model.addAttribute("admin", adminLogin);

        double totalPrice = vehicleService.calculateTotalPriceOfRentedVehicles(); //sammenlagt bil pris pr måned
        model.addAttribute("totalPrice", totalPrice);

        double totalPrices = leaseService.calculateTotalPriceOfLeasingContracts();  //sammenlagt bil pris pr måned.
        model.addAttribute("totalPrices", totalPrices);

        List<Map<String, Object>> rentedCars = vehicleService.getTotalPricesData(); //sammenlagt bil pris pr måned
        model.addAttribute("rentedCars", rentedCars);

        return "sammenlagtpris";
    }
}
