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

        public void createCustomer( Customer customer){
            customerRepository.createCustomer(customer);
        }
        public Customer findId(int id){
            return customerRepository.findCustomerByid(id);
        }
        public String findCustomerid(String email){
            return customerRepository.findIdByEmail(email);
        }

        public List<Customer> fetchAll(){
            return customerRepository.fetchAll();
        }

        public void updateCustomer(Customer customer, int customer_id){
            customerRepository.updateCustomer(customer, customer_id);
        }

    }