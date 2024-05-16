package org.example.carrental.Repository;

import org.example.carrental.model.Vehicle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BusinessDeveloperRepository {

    private final JdbcTemplate jdbcTemplate;

    public BusinessDeveloperRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Vehicle> findAll() {
        // implement database query
        return jdbcTemplate.query("SELECT * FROM vehicle", (rs, rowNum) -> {
            Vehicle car = new Vehicle();

            // Map columns from ResultSet to Car object
            car.setId(rs.getInt("id"));
            car.setModel(rs.getString("name"));
            // Map columns as needed

            return car;
        });
    }


}