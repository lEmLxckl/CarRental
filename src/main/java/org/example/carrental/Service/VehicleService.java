package org.example.carrental.service;

import org.example.carrental.model.Vehicle;
import org.example.carrental.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepo vehicleRepo;

    public List<Vehicle> fetchAll() {
        return vehicleRepo.fetchAll();
    }

    public List<Vehicle> fetchAvailable() {
        return vehicleRepo.fetchAvailable();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepo.addVehicle(vehicle);
    }

    public boolean deleteVehicle(int id) {
        return vehicleRepo.deleteVehicle(id);
    }

    public void updateVehicle(Vehicle vehicle, int id) {
        vehicleRepo.updateVehicle(vehicle, id);
    }

    public void updateAfterContract(int id) {
        vehicleRepo.updateAfterContract(id);
    }

    public Vehicle findVehicleById(int id) {
        return vehicleRepo.findVehicleById(id);
    }

    public double calculateTotalPriceOfRentedVehicles() {
        List<Vehicle> rentedVehicles = vehicleRepo.fetchRentedVehicles();
        double totalPrice = 0.0;
        for (Vehicle vehicle : rentedVehicles) {
            if (vehicle.getFlow() == 1) {
                totalPrice += vehicle.getPrice();
            }
        }
        return totalPrice;
    }

    public void updateAfterDamageReport(int id) {
        vehicleRepo.updateAfterDamageReport(id);
    }

    public List<Map<String, Object>> getTotalPricesData() {
        return vehicleRepo.getTotalPricesData();
    }
}