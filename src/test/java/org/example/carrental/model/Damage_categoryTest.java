package org.example.carrental.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Damage_categoryTest {

    @Test
    void getCategory_id() {
        Damage_category damage_category = new Damage_category();
        damage_category.setCategory_id(1);
        assertEquals(damage_category.getCategory_id(), 1);

    }
}