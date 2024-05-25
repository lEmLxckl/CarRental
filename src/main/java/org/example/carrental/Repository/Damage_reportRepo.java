package org.example.carrental.Repository;

import org.example.carrental.model.Damage_report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Damage_reportRepo {
    @Autowired
    JdbcTemplate template;


    // Hente alle skaderapporter fra databasen.
    public List <Damage_report> fetchAll(){
        String sql = "SELECT * FROM damage_report";
        RowMapper rowMapper = new BeanPropertyRowMapper<>(Damage_report.class);
        return template.query(sql, rowMapper);
    }

    // Formålet er at insætte data fra Damage_report objektet ind i "damage_report" tabellen i databasen ved SQL-forespørgsel.
    public void CreateDamage_report(Damage_report d) {
        String sql = "INSERT INTO damage_report (report_id,total_price,contract_id) VALUES (?,?,?)";
        template.update(sql, d.getReport_id(),d.getTotal_price(),d.getContract_id());
    }

    // Update en eksiterende skade rapport i databsen, værdierne der opdateres er total_price, contract_ id fra en repport id
    public void updateDamageReport(Damage_report damageReport, int report_id ){
        String sql = "UPDATE damage_report SET total_price= ?, contract_id= ? where report_id=?";
        template.update(sql, damageReport.getTotal_price(), damageReport.getContract_id(), damageReport.getReport_id());
    }

    // Find skaderapport i databasen baseret på dens rapport id.
    public Damage_report findDamageReportByid(int report_id) {
        String sql = "Select * FROM damage_report WHERE report_id = ?";
        RowMapper<Damage_report> rowMapper = new BeanPropertyRowMapper<>(Damage_report.class);
        List<Damage_report> users = template.query(sql, rowMapper, report_id);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }

    }

    // Sletter en skaderapport fra databasen baseret på report id, returnere en boolean værdi  der angiver det vellykket
    public boolean deleteReport(int report_id){
        String sql= "DELETE FROM damage_report WHERE report_id=?";
        return template.update(sql,report_id)>0;
    }

}