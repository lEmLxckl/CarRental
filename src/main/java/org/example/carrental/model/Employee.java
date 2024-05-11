package org.example.carrental.model;

public class Employee {
    private String id;
    private String userName;
    private String userPassword;


    public Employee(String id, String userName,String userPassword ) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Employee () {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
