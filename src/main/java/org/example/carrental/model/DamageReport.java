package org.example.carrental.model;

public class DamageReport {
    private String description;
    private String price;
    private int id;

    public String getPrice() {
        return price;
    }
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

