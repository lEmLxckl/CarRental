package org.example.carrental.Repository;

import org.example.carrental.model.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DamageAndPickUpRepo {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DamageAndPickUpRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveDamageAndPickUp(DamageReport damageReport) {
        String query = "INSERT INTO Damage_Report (Describtion, Cost, DateReported, VehicleID) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, damageReport.getDescription(), damageReport.getCost(), damageReport.getDateReported(), damageReport.getVehicleId());
    }

    public List<DamageReport> showAllDamageAndPickUps() {
        String query = "SELECT * FROM Damage_Report";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(DamageReport.class));
    }

    public DamageReport getDamageAndPickUp(int id) {
        String query = "SELECT * FROM Damage_Report WHERE DamageID = ?";
        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(DamageReport.class), id);
    }

    public void updateDamageAndPickUp(DamageReport damageReport) {
        String query = "UPDATE Damage_Report SET Describtion = ?, Cost = ?, DateReported = ?, VehicleID = ? WHERE DamageID = ?";
        jdbcTemplate.update(query, damageReport.getDescription(), damageReport.getCost(), damageReport.getDateReported(), damageReport.getVehicleId(), damageReport.getId());
    }

    public void deleteDamageAndPickUp(int id) {
        String query = "DELETE FROM Damage_Report WHERE DamageID = ?";
        jdbcTemplate.update(query, id);
    }
}