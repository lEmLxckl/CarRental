package org.example.carrental.model;

public class Damage_category {
    private int category_id;
    private String damage_name;
    private double price;


    public Damage_category() {
    }

    public Damage_category(int category_id, String damage_name, double price) {
        this.category_id = category_id;
        this.damage_name = damage_name;
        this.price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getDamage_name() {
        return damage_name;
    }

    public void setDamage_name(String damage_name) {
        this.damage_name = damage_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

