package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Customer;
import org.example.carrental.service.CustomerService;
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
    @GetMapping("/opretlejekontrakt")
    public String lejekontrakt ( Model model, HttpSession session){
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }

        List<Customer> customers = customerService.fetchAll();
        session.setAttribute("customersInLej", customers);
        model.addAttribute("customers", customers);
        return "opretLejekontrakt";
    }

    // Opretter kunde, returnere bekræftelse ved oprettelse af kunde
    @PostMapping("/opretenkunde")
    public String createCustomer (Customer c, Model model, HttpSession session){
        customerService.createCustomer(c);
        model.addAttribute("kunde", "Kunde tilføjet");
        session.setAttribute("kundeoprettet", c);
        System.out.println(c.getCustomer_id());
        return "redirect:/opretNyKundeConfirmed";
    }

    // HttpSession check
    @GetMapping("/opretNyKunde")
    public String CreateNewCustomer(HttpSession session){
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        return "opretNyKunde";
    }

    // Returnere den oprettede kunde

    @GetMapping("/opretNyKundeConfirmed")
    public String newCustomerCreated(HttpSession session, Model model){
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        Customer c = (Customer) session.getAttribute("kundeoprettet");
        String value = customerService.findCustomerid(c.getEmail());
        model.addAttribute("customer", c);
        model.addAttribute("customerid",value);
        return "opretNyKundeConfirmed";

    }


    // modtager customer object baseret på id, tilføjer kundeobjekt som attribut til modellen.
    @GetMapping("/opdaterkunde/{customer_id}")
    public String updateCustomer(@PathVariable("customer_id") int customer_id, Model model, HttpSession session){
        if (!employeeService.checkSession(session)){
            return "redirect:/";
        }
        Customer customer=customerService.findId(customer_id);
        model.addAttribute("opdater", customer);
        return "opdaterKunde";
    }

    // Modtager opdateret kundeinfo, kalder opdatermetode og redirecter til opretlejekontrakt ved udførelse
    @PostMapping("/opdaterkunden")
    public String updateTheCustomer(Customer c,int customer_id){
        customerService.updateCustomer(c, customer_id);
        return "redirect:/opretlejekontrakt";
    }

}