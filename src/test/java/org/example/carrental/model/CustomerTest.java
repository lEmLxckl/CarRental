package org.example.carrental.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void getCustomer_id() {
        Customer customer = new Customer();
        customer.setCustomer_id(1);
        assertEquals(1, customer.getCustomer_id());

    }
}