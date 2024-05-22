package org.example.carrental.Repository;

import org.example.carrental.model.Lease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeaseRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;
    // SQL-query selects all rows in lease table and maps them to LeasingContract objects
    public List<Lease> fetchAll() {
        String sql = "SELECT * FROM lease";
        RowMapper<Lease> rowMapper = new BeanPropertyRowMapper<>(Lease.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    // SQL-query selects all leases where stat is 1
    public List<Lease> fetchFlow1() {
        String sql = "SELECT * FROM lease WHERE stat = 1";
        RowMapper<Lease> rowMapper = new BeanPropertyRowMapper<>(Lease.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    // SQL-query inserts a new lease record
    public void createLeasingContract(Lease leasingContract) {
        String sql = "INSERT INTO lease (type_of_lease, start_date, end_date, stat, customer_id, vehicle_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, leasingContract.getTypeOfLease(), leasingContract.getStartDate(), leasingContract.getEndDate(), leasingContract.getPrice(), leasingContract.getCustomerID(), leasingContract.getVehicleID());
    }

    // SQL-query selects a specific lease by id
    public Lease findContractById(int leaseId) {
        String sql = "SELECT * FROM lease WHERE lease_id = ?";
        RowMapper<Lease> rowMapper = new BeanPropertyRowMapper<>(Lease.class);
        List<Lease> contracts = jdbcTemplate.query(sql, rowMapper, leaseId);
        if (contracts.size() == 1) {
            return contracts.get(0);
        } else {
            return null;
        }
    }

    // SQL-query selects a specific lease by id where stat is 1
    public Lease findContractByIdAndFlow(int leaseId) {
        String sql = "SELECT * FROM lease WHERE stat = 1 AND lease_id = ?";
        RowMapper<Lease> rowMapper = new BeanPropertyRowMapper<>(Lease.class);
        List<Lease> contracts = jdbcTemplate.query(sql, rowMapper, leaseId);
        if (contracts.size() == 1) {
            return contracts.get(0);
        } else {
            return null;
        }
    }
}
