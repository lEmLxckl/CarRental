package org.example.carrental.model;

public class Employee {
    private String username;
    private String user_password;
    private String full_name;
    private String email;
    private String phone;
    private int is_active;
    private int is_admin;
    private Usertype usertype;


    public Employee() {
    }

    public Employee(String username, String user_password, String full_name, String email, String phone, int is_active, int is_admin, Usertype usertype) {
        this.username = username;
        this.user_password = user_password;
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.is_active = is_active;
        this.is_admin = is_admin;
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
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

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public Usertype getUsertype() {
        return usertype;
    }

    public void setUsertype(Usertype usertype) {
        this.usertype = usertype;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                ", user_password='" + user_password + '\'' +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", is_active=" + is_active +
                ", is_admin=" + is_admin +
                ", usertype=" + usertype +
                '}';
    }
}