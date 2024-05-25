package org.example.carrental.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void getUsername() {
        Employee employee = new Employee();
        employee.setUsername("admin");
        assertEquals("admin", employee.getUsername());

    }
}