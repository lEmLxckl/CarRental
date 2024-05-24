package org.example.carrental.service;

import org.example.carrental.model.Car;
import org.example.carrental.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepo carRepo;

    public List<Car> fetchAll() {
        return carRepo.fetchAll();
    }

    public List<Car>fetchAvailable(){
        return carRepo.fetchAvailable();
    }
    public void addCar( Car car){
        carRepo.addCar(car);
    }
    public boolean deleteCar(int id){
        return carRepo.deleteCar(id);
    }

    public void updateCar(Car car, int id){
        carRepo.updateCar(car, id);
    }
    public void updateAfterContract(int id){
        carRepo.updateAfterContract(id);
    }
    public Car findId(int id){
        return carRepo.findCarByid(id);
    }

    // bruger list til at indholde bilerne og udfra dette beregner og summere alle bilernes pris sammen.
    public double calculateTotalPriceOfRentedCars() {
        List<Car> rentedCars = carRepo.fetchRentedCars(); // Hent de udlejet biler
        double totalPrice = 0.0;

        for (Car car : rentedCars) {
            if (car.getFlow() == 1) { // Tjek hvis bil er udlejet (flow = 1)
                totalPrice += car.getPrice(); // Tilføj bilens pris til den totale pris
            }
        }

        return totalPrice;
    }


    public void updateAfterDamageReport(int id){
        carRepo.updateAfterDamageReport(id);
    }
    public List<Map<String, Object>> TotalpriceData() {
        return carRepo.getTotalPricesData();
    }
}