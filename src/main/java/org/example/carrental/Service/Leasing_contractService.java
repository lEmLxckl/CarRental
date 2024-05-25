package org.example.carrental.Service;

import org.example.carrental.Repository.Leasing_contractRepo;
import org.example.carrental.model.Leasing_contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Leasing_contractService {
    @Autowired
    Leasing_contractRepo leasing_contractRepo;


    public List <Leasing_contract> fetchAll() {
        return leasing_contractRepo.fetchAll();
    }
    public List <Leasing_contract> fetchFlow1() {
        return leasing_contractRepo.fetchFlow1();
    }


    public void addLeasingContract(Leasing_contract leasing_contract){
        leasing_contractRepo.createLeasingContract(leasing_contract);
    }
    public double calculateTotalPriceOfLeasingContracts() {
        List<Leasing_contract> leasingContracts = leasing_contractRepo.fetchAll(); //Henter alle kontrakter
        double totalPrice = 0.0;

        for (Leasing_contract contract : leasingContracts) {
            totalPrice += contract.getPrice();// Tilføj kontrakt pris til den totale pris
        }

        return totalPrice;
    }

    public Leasing_contract findIdAndFlow(int id){
        return leasing_contractRepo.findContractByidAndFlow(id);
    }
}