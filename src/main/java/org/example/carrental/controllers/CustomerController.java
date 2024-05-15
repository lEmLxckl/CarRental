package org.example.carrental.controllers;

import org.example.carrental.model.Customer;
import org.example.carrental.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/addCustomerForm")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "home/addCustomerForm";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.createCustomer(customer);
        return "redirect:/";
    }
}
