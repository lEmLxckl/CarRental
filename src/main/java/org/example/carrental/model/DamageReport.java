package org.example.carrental.model;

public class DamageReport {
    private String description;
    private String price;
    private int id;

    public DamageReport(String description, String price, int id) {
        this.description = description;
        this.price = price;
        this.id = id;
    }

    public DamageReport() {

    }

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

    @Override
    public String toString() {
        return "DamageReport{" +
                "description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", id=" + id +
                '}';
    }
}

