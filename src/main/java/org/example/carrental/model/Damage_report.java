package org.example.carrental.model;

public class Damage_report {
    private int report_id;
    private double total_price;
    private int contract_id;
    private String username;

    public Damage_report() {
    }

    public Damage_report(int report_id, double total_price, int contract_id, String username ) {
        this.report_id = report_id;
        this.total_price = total_price;
        this.contract_id = contract_id;
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    @Override
    public String toString() {
        return "Damage_report{" +
                "report_id=" + report_id +
                ", total_price=" + total_price +
                ", contract_id=" + contract_id +
                ", username='" + username + '\'' +
                '}';
    }
}