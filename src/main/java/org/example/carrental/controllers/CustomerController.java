package org.example.carrental.controllers;

import org.example.carrental.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

}
