package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.Service.Leasing_contractService;
import org.example.carrental.Service.VehicleService;
import org.example.carrental.model.Employee;
import org.example.carrental.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class VehicleController {

    @Autowired
    VehicleService carService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    Leasing_contractService leasing_contractService;

    // Dette view viser en liste af alle biler
    @GetMapping("/viewAllCars")
    public String viewAllCars(Model model, HttpSession session) {
        // her tjekker den hvis employee stadig er i session,
        //før den kan udfører handlinger i systemet
        // den findes stort set i alle controller metoderne
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "viewAllCars";
    }

    // Dette view viser alle ledige biler
    @GetMapping("/availableCars")
    public String viewAvailableCars(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Car> availableCars = carService.getAvailable();
        model.addAttribute("available", availableCars);
        return "availableCars";
    }

    // Dette tilføjer nye biler til systemet
    @GetMapping("/addCar")
    public String addCar(HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        return "addCar";
    }

    // Her gemmer den biler
    @PostMapping("/createCar")
    public String createCar(@ModelAttribute Car car, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        carService.addCar(car);
        return "redirect:/viewAllCars";
    }

    // Her sletter den biler
    @GetMapping("/deleteCar/{vehicle_number}")
    public String deleteCar(@PathVariable("vehicle_number") int vehicle_number, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        boolean deleted = carService.deleteCar(vehicle_number);
        return "redirect:/viewAllCars";
    }

    // Her opdatere den bilerne
    @GetMapping("/updateCar/{vehicle_number}")
    public String updateCar(@PathVariable("vehicle_number") int vehicle_number, Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Car car = carService.findId(vehicle_number);
        model.addAttribute("update", car);
        return "updateCar";
    }

    // Her gemmer den de opdaterede bil detaljer
    @PostMapping("/carUpdate")
    public String updateCarDetails(Car car, int vehicle_number) {
        carService.updateCar(car, vehicle_number);
        return "redirect:/viewAllCars";
    }

    // Her viser den total pris på alle udlejede biler
    @GetMapping("/totalPrice")
    public String getTotalPrice(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Employee adminLogin = (Employee) session.getAttribute("adminlogin");
        model.addAttribute("admin", adminLogin);

        double totalPrice = carService.calculateTotalPriceOfRentedCars();
        model.addAttribute("totalPrice", totalPrice);

        double totalPrices = leasing_contractService.calculateTotalPriceOfLeasingContracts();
        model.addAttribute("totalPrices", totalPrices);

        List<Map<String, Object>> rentedCars = carService.TotalpriceData();
        model.addAttribute("rentedCars", rentedCars);

        return "totalPrice";
    }
}
