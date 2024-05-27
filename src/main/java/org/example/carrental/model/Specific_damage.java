package org.example.carrental.model;

public class Specific_damage {
    private int specific_damage_id;
    private int report_id;
    private int category_id;

    public Specific_damage() {
    }

    public Specific_damage(int specific_damage_id, int report_id, int category_id) {
        this.specific_damage_id = specific_damage_id;
        this.report_id = report_id;
        this.category_id = category_id;
    }

    public int getSpecific_damage_id() {
        return specific_damage_id;
    }

    public void setSpecific_damage_id(int specific_damage_id) {
        this.specific_damage_id = specific_damage_id;
    }

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Specific_damage{" +
                "specific_damage_id=" + specific_damage_id +
                ", report_id=" + report_id +
                ", category_id=" + category_id +
                '}';
    }
}