package org.example.carrental.Repository;

import org.example.carrental.model.Leasing_contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Leasing_contractRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // SQL-query vælger alle rows i leasing_contract tabellen og indsætter dem i rowmapper
    public List<Leasing_contract> fetchAll(){
        String sql = "SELECT * FROM leasing_contract";
        RowMapper<Leasing_contract> rowMapper = new BeanPropertyRowMapper<>(Leasing_contract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    // SQL-query vælger alle leasingcontract rows i et join table hvor car-flow er = 1
    public List<Leasing_contract> fetchFlow1(){
        String sql = "SELECT * FROM leasing_contract Join car using(vehicle_number) where flow = 1;";
        RowMapper<Leasing_contract> rowMapper = new BeanPropertyRowMapper<>(Leasing_contract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    // SQL-query opretter leasing_contract
    public void createLeasingContract(Leasing_contract leasingContract) {
        String sql = "INSERT INTO leasing_contract (start_date, end_date, price, vehicle_number, username, customer_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, leasingContract.getStart_date(), leasingContract.getEnd_date(), leasingContract.getPrice(),
                leasingContract.getVehicle_number(), leasingContract.getUsername(), leasingContract.getCustomer_id());
    }

    // lister en bestemt leasing_contract med et tilhørende Id, i en liste ved navn users.
    public Leasing_contract findContractByid(int contract_id) {
        String sql = "Select * FROM leasing_contract WHERE contract_id = ?";
        RowMapper< Leasing_contract> rowMapper = new BeanPropertyRowMapper<>( Leasing_contract.class);
        List< Leasing_contract> users = jdbcTemplate.query(sql, rowMapper, contract_id);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }

    // SQL-query vælger alle leasingcontract rows i et join table hvor car-flow er = 1 og contract_id angivet
    public Leasing_contract findContractByidAndFlow(int contract_id) {
        String sql = "SELECT * FROM leasing_contract Join car using(vehicle_number) where flow = 1 and contract_id = ?";
        RowMapper< Leasing_contract> rowMapper = new BeanPropertyRowMapper<>( Leasing_contract.class);
        List< Leasing_contract> users = jdbcTemplate.query(sql, rowMapper, contract_id);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }


}