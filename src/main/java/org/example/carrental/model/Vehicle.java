package org.example.carrental.model;

import java.math.BigDecimal;
import java.util.Date;

public class Vehicle {
    private int id;
    private String brand;
    private Date releaseDate;
    private int serialNumber;
    private String licensePlate;
    private String model;
    private String equipmentLevel;
    private BigDecimal regTax;
    private BigDecimal co2Discharge;
    private int price;
    private int flow;



    public Vehicle() {
    }

    public Vehicle(int id, String brand, Date releaseDate, int serialNumber, String licensePlate,
                   String model, String equipmentLevel, BigDecimal price, BigDecimal regTax,
                   BigDecimal co2Discharge) {
        this.id = id;
        this.brand = brand;
        this.releaseDate = releaseDate;
        this.serialNumber = serialNumber;
        this.licensePlate = licensePlate;
        this.model = model;
        this.equipmentLevel = equipmentLevel;
        this.regTax = regTax;
        this.co2Discharge = co2Discharge;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(String equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }


    public BigDecimal getRegTax() {
        return regTax;
    }

    public void setRegTax(BigDecimal regTax) {
        this.regTax = regTax;
    }

    public BigDecimal getCo2Discharge() {
        return co2Discharge;
    }

    public void setCo2Discharge(BigDecimal co2Discharge) {
        this.co2Discharge = co2Discharge;
    }
    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



}