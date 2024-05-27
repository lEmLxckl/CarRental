package org.example.carrental.model;

public class Customer {
    private int customer_id;
    private String full_name;
    private String email;
    private String phone;
    private String address;
    private String cpr;

    public Customer() {
    }

    public Customer(int customer_id, String full_name, String email, String phone, String address, String cpr) {
        this.customer_id = customer_id;
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.cpr = cpr;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", cpr='" + cpr + '\'' +
                '}';
    }
}