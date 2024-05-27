package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Customer;
import org.example.carrental.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;

    // Returnere liste af alle kunder
    @GetMapping("/createLeaseContract")
    public String createLeaseContract(Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        List<Customer> customers = customerService.getAllCustomers();
        session.setAttribute("customersInLease", customers);
        model.addAttribute("customers", customers);
        return "createLeaseContract";
    }

    // Opretter kunde, returnere bekræftelse ved oprettelse af kunde
    @PostMapping("/createCustomer")
    public String createCustomer(Customer c, Model model, HttpSession session) {
        customerService.createCustomer(c);
        model.addAttribute("customer", "Customer added");
        session.setAttribute("customerCreated", c);
        System.out.println(c.getCustomer_id());
        return "redirect:/createCustomerConfirmed";
    }

    // HttpSession check
    @GetMapping("/createNewCustomer")
    public String createNewCustomer(HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        return "createCustomer";
    }

    // Returnere den oprettede kunde
    @GetMapping("/createCustomerConfirmed")
    public String customerCreated(HttpSession session, Model model) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Customer c = (Customer) session.getAttribute("customerCreated");
        String value = customerService.findCustomerid(c.getEmail());
        model.addAttribute("customer", c);
        model.addAttribute("customerId", value);
        return "createCustomerConfirmed";
    }

    // modtager customer object baseret på id, tilføjer kundeobjekt som attribut til modellen.
    @GetMapping("/updateCustomer/{customer_id}")
    public String updateCustomer(@PathVariable("customer_id") int customer_id, Model model, HttpSession session) {
        if (!employeeService.checkSession(session)) {
            return "redirect:/";
        }
        Customer customer = customerService.findId(customer_id);
        model.addAttribute("update", customer);
        return "updateCustomer";
    }

    // Modtager opdateret kundeinfo, kalder opdatermetode og redirecter til opretlejekontrakt ved udførelse
    @PostMapping("/updateCustomerInfo")
    public String updateCustomerInfo(Customer c, int customer_id) {
        customerService.updateCustomer(c, customer_id);
        return "redirect:/createLeaseContract";
    }
}
