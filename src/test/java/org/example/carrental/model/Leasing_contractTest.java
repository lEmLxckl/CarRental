package org.example.carrental.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Leasing_contractTest {

    @Test
    void getContract_id() {
        Leasing_contract contract = new Leasing_contract();
        assertEquals(contract.getContract_id(), 0);

    }
}