package org.example.carrental.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void getOdometer() {
        Car car = new Car();
        car.setOdometer(5);
        assertEquals(5, car.getOdometer());
    }
}