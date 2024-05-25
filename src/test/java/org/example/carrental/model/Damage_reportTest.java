package org.example.carrental.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Damage_reportTest {

    @Test
    void getUsername() {
        Damage_report damage_report = new Damage_report();
        damage_report.setUsername("test");
        assertEquals("test", damage_report.getUsername());

    }
}