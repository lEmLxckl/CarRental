package org.example.carrental.repository;

import org.example.carrental.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class VehicleRepo {

    @Autowired
    JdbcTemplate template;

    // Return a list of all vehicles
    public List<Vehicle> fetchAll() {
        String sql = "SELECT * FROM vehicle";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper);
    }

    // Return a list of available vehicles
    public List<Vehicle> fetchAvailable() {
        String sql = "SELECT * FROM vehicle WHERE flow = 0";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper);
    }

    // Add a vehicle
    public void addVehicle(Vehicle v) {
        String sql = "INSERT INTO vehicle (brand, releasedate, serialnumber, licenseplate, model, equipmentlevel, price, regtax, co2discharge, flow) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, v.getBrand(), v.getReleaseDate(), v.getSerialNumber(), v.getLicensePlate(), v.getModel(), v.getEquipmentLevel(), v.getPrice(), v.getRegTax(), v.getCo2Discharge(), v.getFlow());
    }

    // Delete a vehicle
    public Boolean deleteVehicle(int id) {
        String sql = "DELETE FROM vehicle WHERE id = ?";
        return template.update(sql, id) > 0;
    }

    // Find a vehicle by id
    public Vehicle findVehicleById(int id) {
        String sql = "SELECT * FROM vehicle WHERE id = ?";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        List<Vehicle> vehicles = template.query(sql, rowMapper, id);
        if (vehicles.size() == 1) {
            return vehicles.get(0);
        } else {
            return null;
        }
    }

    // Update a vehicle
    public void updateVehicle(Vehicle v, int id) {
        String sql = "UPDATE vehicle SET brand = ?, releasedate = ?, serialnumber = ?, licenseplate = ?, model = ?, equipmentlevel = ?, price = ?, regtax = ?, co2discharge = ?, flow = ? WHERE id = ?";
        template.update(sql, v.getBrand(), v.getReleaseDate(), v.getSerialNumber(), v.getLicensePlate(), v.getModel(), v.getEquipmentLevel(), v.getPrice(), v.getRegTax(), v.getCo2Discharge(), v.getFlow(), id);
    }

    // Mark a vehicle as rented (flow = 1)
    public void updateAfterContract(int id) {
        String sql = "UPDATE vehicle SET flow = 1 WHERE id = ?";
        template.update(sql, id);
    }

    // Fetch rented vehicles (flow = 1)
    public List<Vehicle> fetchRentedVehicles() {
        String sql = "SELECT * FROM vehicle WHERE flow = 1";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper);
    }

    // Mark a vehicle after a damage report (flow = 2)
    public void updateAfterDamageReport(int id) {
        String sql = "UPDATE vehicle SET flow = 2 WHERE id = ?";
        template.update(sql, id);
    }

    // Join table to get total prices data
    public List<Map<String, Object>> getTotalPricesData() {
        String sql = "SELECT vehicle.id, vehicle.brand, vehicle.model, vehicle.flow, leasing_contract.contract_id, leasing_contract.username, leasing_contract.customer_id, leasing_contract.start_date, leasing_contract.end_date, vehicle.price AS vehicle_price, leasing_contract.price AS contract_price FROM vehicle JOIN leasing_contract ON vehicle.id = leasing_contract.vehicle_id WHERE vehicle.flow = 1";
        return template.queryForList(sql);
    }
}