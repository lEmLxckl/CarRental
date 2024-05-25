package org.example.carrental.Repository;

import org.example.carrental.model.Car;
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

    // Returner en liste af  biler
    public List<Car> fetchAll() {
        String sql = "SELECT * FROM Car";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    //returner en liste af ledig biler
    public List<Car> fetchAvailable() {
        String sql = "SELECT * FROM Car WHERE flow = 0";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    // Tilføj bil
    public void addCar(Car c) {
        String sql = "INSERT INTO car (vehicle_number,frame_number, " +
                "brand, model, make, color, price, flow, odometer, fuel_type, motor, gear_type)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        template.update(sql, c.getVehicle_number(), c.getFrame_number(), c.getBrand(), c.getModel(), c.getMake(),
                c.getColor(), c.getPrice(), c.getFlow(), c.getOdometer(), c.getFuel_type(), c.getMotor(), c.getGear_type());
    }

    //Slet bil
    public Boolean deleteCar(int vehicle_number) {
        String sql = "DELETE FROM car WHERE vehicle_number = ?";
        return template.update(sql, vehicle_number) > 0;
    }

    // find en bil hvor vehicle_number er ?
    public Car findCarByid(int vehicle_number) {
        String sql = "Select * FROM car WHERE vehicle_number = ?";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        List<Car> users = template.query(sql, rowMapper, vehicle_number);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }

    }


    // opdater bil
    public void updateCar(Car c, int vehicle_number) {
        String sql = "UPDATE car SET frame_number = ?, brand = ?, model = ?, make = ?, color = ?, price = ?, flow = ?, odometer = ?, fuel_type = ?, motor = ?, gear_type = ? WHERE vehicle_number = ?";
        template.update(sql, c.getFrame_number(), c.getBrand(), c.getModel(), c.getMake(), c.getColor(), c.getPrice(), c.getFlow(), c.getOdometer(), c.getFuel_type(), c.getMotor(), c.getGear_type(), c.getVehicle_number());
    }

    //gør en bil til flow 1 altså en udlejet bil
    public void updateAfterContract(int vehicle_number) {
        String sql = "UPDATE car SET flow = 1 WHERE vehicle_number = ?";
        template.update(sql, vehicle_number);
    }

    //Hent udlejet biler (flow 1)
    public List<Car> fetchRentedCars() {
        String sql = "SELECT * FROM car WHERE flow = 1";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    // Gør en bil til flow 2 når der er blevet lavet en skaderapport
    public void updateAfterDamageReport(int vehicle_number) {
        String sql = "UPDATE car SET flow = 2 WHERE vehicle_number = ?";
        template.update(sql, vehicle_number);
    }

    // lav en join tabel til sammenlagt priser
    public List<Map<String, Object>> getTotalPricesData() {
        String sql = "SELECT car.vehicle_number, car.frame_number, car.brand, car.flow, leasing_contract.contract_id," +
                " leasing_contract.username, leasing_contract.customer_id, leasing_contract.start_date, leasing_contract.end_date, car.price" +
                " AS car_price, leasing_contract.price AS contract_price " +
                "FROM car " +
                "JOIN leasing_contract ON car.vehicle_number = leasing_contract.vehicle_number WHERE flow = 1";
        return template.queryForList(sql);
    }

}