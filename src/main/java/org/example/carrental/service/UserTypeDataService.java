package org.example.carrental.service;

import org.example.carrental.model.LeaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserTypeDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createLease(int customerId, int vehicleId, LeaseType type, LocalDate startDate, LocalDate endDate) {
        String query = "INSERT INTO Lease (CustomerID, VehicleID, TypeOfLease, StartDate, EndDate) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, customerId, vehicleId, type.name(), startDate, endDate);
    }

}