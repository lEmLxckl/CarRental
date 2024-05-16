package org.example.carrental.Service;

import org.example.carrental.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessDeveloperService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Method to fetch all vehicles
    public List<Vehicle> getAllVehicles() {
        return jdbcTemplate.query(
                "SELECT * FROM vehicle",
                (rs, rowNum) -> {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(rs.getInt("id"));
                    vehicle.setModel(rs.getString("model"));
                    vehicle.setPrice(rs.getDouble("price"));
                    vehicle.setLeased(rs.getBoolean("leased"));
                    return vehicle;
                });
    }

    // Method to fetch vehicles sorted by price
    public List<Vehicle> getAllVehiclesSortedByPrice() {
        return jdbcTemplate.query(
                "SELECT * FROM vehicle ORDER BY price",
                (rs, rowNum) -> {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(rs.getInt("id"));
                    vehicle.setModel(rs.getString("model"));
                    vehicle.setPrice(rs.getDouble("price"));
                    vehicle.setLeased(rs.getBoolean("leased"));
                    return vehicle;
                });
    }

    // Method to fetch leased vehicles
    public List<Vehicle> getLeasedVehicles() {
        return jdbcTemplate.query(
                "SELECT * FROM vehicle WHERE leased = true",
                (rs, rowNum) -> {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(rs.getInt("id"));
                    vehicle.setModel(rs.getString("model"));
                    vehicle.setPrice(rs.getDouble("price"));
                    vehicle.setLeased(rs.getBoolean("leased"));
                    return vehicle;
                });
    }

    // Method to fetch vehicles sorted by8 model
    public List<Vehicle> getAllVehiclesSortedByModel() {
        return jdbcTemplate.query(
                "SELECT * FROM vehicle ORDER BY model",
                (rs, rowNum) -> {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(rs.getInt("id"));
                    vehicle.setModel(rs.getString("model"));
                    vehicle.setPrice(rs.getDouble("price"));
                    vehicle.setLeased(rs.getBoolean("leased"));
                    return vehicle;
                });
    }

}