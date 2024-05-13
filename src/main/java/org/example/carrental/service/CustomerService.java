package org.example.carrental.service;

import org.example.carrental.model.Customer;
import org.example.carrental.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(int id, Customer customer) {
        Customer existingCustomer = customerRepository.getCustomerById(id);
        if (existingCustomer != null) {
            customerRepository.update(id, customer);
            return customer;
        } else {
            return null; // Customer with given ID not found
        }
    }

    public boolean deleteCustomer(int id) {
        Customer existingCustomer = customerRepository.getCustomerById(id);
        if (existingCustomer != null) {
            customerRepository.deleteById(id);
            return true; // Customer deleted successfully
        } else {
            return false; // Customer with given ID not found
        }
    }
}