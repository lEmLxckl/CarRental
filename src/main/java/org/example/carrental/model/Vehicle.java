package org.example.carrental.model;

public class Car {

    private int id;

    private String make;
    private String model;
    private double price;
    private boolean leased;


    public Car() {
    }

    public Car(String make, String model, double price, boolean leased) {
        this.make = make;
        this.model = model;
        this.price = price;
        this.leased = leased;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isLeased() {
        return leased;
    }

    public void setLeased(boolean leased) {
        this.leased = leased;
    }
}
