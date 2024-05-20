package org.example.carrental.model;

public class Employee {
    private int id;
    private String userName;
    private String userPassword;
    private Usertype usertype;


    public Employee(int id, String userName,String userPassword, Usertype usertype ) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.usertype = usertype;
    }

    public Employee () {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", usertype=" + usertype +
                '}';
    }
}
