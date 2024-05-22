package org.example.carrental.service;

import org.example.carrental.Repository.LeaseRepo;
import org.example.carrental.model.Lease;
import org.example.carrental.model.LeaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaseService {
    @Autowired
    LeaseRepo leaseRepo;

    public List<Lease> fetchAll() {
        return leaseRepo.fetchAll();
    }

    public List<Lease> fetchFlow1() {
        return leaseRepo.fetchFlow1();
    }

    public void addLeasingContract(Lease leasingContract) {
        leaseRepo.createLeasingContract(leasingContract);
    }

    public double calculateTotalPriceOfLeasingContracts() {
        List<Lease> leasingContracts = leaseRepo.fetchAll(); // Fetch all contracts
        double totalPrice = 0.0;

        for (Lease contract : leasingContracts) {
            totalPrice += contract.getPrice(); // Add contract price to the total price
        }

        return totalPrice;
    }

    public Lease findIdAndFlow(int id) {
        return leaseRepo.findContractByIdAndFlow(id);
    }
}