package org.example.carrental.Repository;

import org.example.carrental.model.Damage_category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DamageRepo {
    @Autowired
    JdbcTemplate template;

    // Metoden returnerer en liste over alle skadeskategorier.
    public List<Damage_category> getAllDamageCategories() {
            String sql = "SELECT * FROM damage_category";
            RowMapper<Damage_category> rowMapper = new BeanPropertyRowMapper<>(Damage_category.class);
            return template.query(sql, rowMapper);
        }


    // Metoden tilføjer en skadeskategori til databasen.
    public void AddDamage(Damage_category damageCategory) {
        String sql = "INSERT INTO damage_category (name, price) VALUES (?, ?)";
        template.update(sql, damageCategory.getName(), damageCategory.getPrice());
    }

    // Metoden opdaterer en skadeskategori i databasen.
    public void updateDamage(Damage_category damage_category) {
        // Definerer en SQL-forespørgsel for at opdatere skadeskategorien med de nye værdier.
        String sql = "UPDATE damage_category SET name= ?, price= ? where id=?";

        // Udfører SQL-forespørgslen ved hjælp af JdbcTemplate-objektet og opdaterer skadeskategorien med de angivne værdier.
        template.update(sql, damage_category.getName(), damage_category.getPrice(), damage_category.getId());
    }

    public Boolean deleteDamage(Damage_category category_id) {
        String sql = "DELETE FROM damage_category WHERE id = ?";
        return template.update(sql, category_id.getId()) > 0;
    }

    // Metoden finder en skadeskategori i databasen baseret på kategori-id.
    public Damage_category findDamageByid(int category_id) {
        // Definerer en SQL-forespørgsel for at hente skadeskategorien med det angivne kategori-id.
        String sql = "SELECT * FROM damage_category WHERE id = ?";

        // Opretter en RowMapper til at mappe rækken fra resultatet af SQL-forespørgslen til en skadeskategoriklasse.
        RowMapper<Damage_category> rowMapper = new BeanPropertyRowMapper<>(Damage_category.class);

        // Udfører SQL-forespørgslen ved hjælp af JdbcTemplate-objektet og returnerer resultatet som en liste af skadeskategorier.
        List<Damage_category> categories = template.query(sql, rowMapper, category_id);

        // Hvis der kun findes én skadeskategori med det angivne kategori-id, returneres den.
        if (categories.size() == 1) {
            return categories.get(0);
        } else {
            return null; // found eller flere end en blev fundet == returneres null.
        }
    }

    // Her finder metoden prisen på en specifik damage categorie via id
    public Double getDamagePrice(int id) {
        // Definerer en SQL-forespørgsel
        String sql = "SELECT price FROM damage_category WHERE id = ?";
        try {
            // Udfører SQL-forespørgslen ved hjælp af JdbcTemplate-objektet og returnerer prisen som et Double-objekt.
            return template.queryForObject(sql, Double.class, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}