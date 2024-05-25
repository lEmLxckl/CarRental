package org.example.carrental.Repository;

import org.example.carrental.model.Damage_category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@ComponentScan(basePackages = "com.your.package")  // replace with your actual package
public class DamageRepoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DamageRepo damageCategoryRepository;  // replace with your actual repository class

    @BeforeEach
    void setUp() {
        // Create the damage_category table and insert initial data
        jdbcTemplate.execute("CREATE TABLE damage_category (" +
                "category_id INT PRIMARY KEY, " +
                "damage_name VARCHAR(255), " +
                "price DECIMAL)");

        jdbcTemplate.update("INSERT INTO damage_category (category_id, damage_name, price) VALUES (1, 'Scratch', 100.0)");
        jdbcTemplate.update("INSERT INTO damage_category (category_id, damage_name, price) VALUES (2, 'Dent', 200.0)");
    }

    @Test
    void testFindDamageByid() {
        // When
        Damage_category damageCategory = damageCategoryRepository.findDamageByid(1);

        // Then
        assertThat(damageCategory).isNotNull();
        assertThat(damageCategory.getCategory_id()).isEqualTo(1);
        assertThat(damageCategory.getDamage_name()).isEqualTo("Scratch");
        assertThat(damageCategory.getPrice()).isEqualTo(100.0);
    }

    @Test
    void testFindDamageByidNotFound() {
        // When
        Damage_category damageCategory = damageCategoryRepository.findDamageByid(999);

        // Then
        assertThat(damageCategory).isNull();
    }

    @Test
    void testUpdateDamage() {
        // Given
        Damage_category updatedCategory = new Damage_category();
        updatedCategory.setCategory_id(1);
        updatedCategory.setDamage_name("Updated Scratch");
        updatedCategory.setPrice(150.0);

        // When
        damageCategoryRepository.updateDamage(updatedCategory, 1);

        // Then
        Damage_category retrievedCategory = damageCategoryRepository.findDamageByid(1);
        assertThat(retrievedCategory).isNotNull();
        assertThat(retrievedCategory.getDamage_name()).isEqualTo("Updated Scratch");
        assertThat(retrievedCategory.getPrice()).isEqualTo(150.0);
    }
}