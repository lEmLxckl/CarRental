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

    public void saveDamageAndPickUp(DamageReport damgeAndPickUp) {
        String query = "INSERT INTO damage_report (Describtion, price) VALUES (?, ?)";
        jdbcTemplate.update(query, damgeAndPickUp.getDescription(), damgeAndPickUp.getPrice());
    }

    public List<DamageReport> showAllDamageAndPickUps() {
        String query = "SELECT * FROM damage_report";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(DamageReport.class));
    }

    public DamageReport getDamageAndPickUp(int id) {
        String query = "SELECT * FROM damage_report WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(DamageReport.class), id);
    }

    public void updateDamageAndPickUp(DamageReport damageReport) {
        String query = "UPDATE damage_report SET Describtion = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(query, damageReport.getDescription(), damageReport.getPrice(), damageReport.getId());
    }

    public void deleteDamageAndPickUp(int id) {
        String query = "DELETE FROM damage_report WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}

