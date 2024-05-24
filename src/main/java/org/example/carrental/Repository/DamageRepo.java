package org.example.carrental.Repository;

import org.example.carrental.model.Damage_category;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Damage_category> fetchAll() {
        // Definerer en SQL-forespørgsel for at hente alle poster fra skadeskategoritabellen.
        String sql = "SELECT * FROM damage_category";

        // Opretter en RowMapper til at mappe rækkerne fra resultatet af SQL-forespørgslen til skadeskategoriklassen.
        RowMapper<Damage_category> rowMapper = new BeanPropertyRowMapper<>(Damage_category.class);

        // Udfører SQL-forespørgslen ved hjælp af JdbcTemplate-objektet og returnerer resultatet som en liste af skadeskategorier.
        return template.query(sql, rowMapper);
    }


    // Metoden tilføjer en skadeskategori til databasen.
    public void AddDamage(Damage_category d) {
        // Definerer en SQL-forespørgsel for at indsætte en ny skadeskategori med de tilhørende værdier.
        String sql = "INSERT INTO damage_category (category_id, damage_name, price) VALUES (?,?,?)";

        // Udfører SQL-forespørgslen ved hjælp af JdbcTemplate-objektet og indsætter de nødvendige værdier.
        template.update(sql, d.getCategory_id(),d.getDamage_name(),d.getPrice());
    }

    // Metoden opdaterer en skadeskategori i databasen.
    public void updateDamage(Damage_category damage_category, int category_id) {
        // Definerer en SQL-forespørgsel for at opdatere skadeskategorien med de nye værdier.
        String sql = "UPDATE damage_category SET damage_name= ?, price= ? where category_id=?";

        // Udfører SQL-forespørgslen ved hjælp af JdbcTemplate-objektet og opdaterer skadeskategorien med de angivne værdier.
        template.update(sql, damage_category.getDamage_name(), damage_category.getPrice(), damage_category.getCategory_id());
    }

    public Boolean deleteDamage(int category_id) {
        String sql = "DELETE FROM damage_category WHERE category_id = ?";
        return template.update(sql, category_id) > 0;
    }

    // Metoden finder en skadeskategori i databasen baseret på kategori-id.
    public Damage_category findDamageByid(int category_id) {
        // Definerer en SQL-forespørgsel for at hente skadeskategorien med det angivne kategori-id.
        String sql = "SELECT * FROM damage_category WHERE category_id = ?";

        // Opretter en RowMapper til at mappe rækken fra resultatet af SQL-forespørgslen til en skadeskategoriklasse.
        RowMapper<Damage_category> rowMapper = new BeanPropertyRowMapper<>(Damage_category.class);

        // Udfører SQL-forespørgslen ved hjælp af JdbcTemplate-objektet og returnerer resultatet som en liste af skadeskategorier.
        List<Damage_category> categories = template.query(sql, rowMapper, category_id);

        // Hvis der kun findes én skadeskategori med det angivne kategori-id, returneres den.
        if (categories.size() == 1) {
            return categories.get(0);
        } else {
            return null; // Hvis ingen skadeskategori blev fundet eller flere end én blev fundet, returneres null.
        }
    }



    // Metoden finder prisen på en bestemt skadeskategori i databasen baseret på kategori-id.
    public Double findSpecificDamagePrice(int category_id) {
        // Definerer en SQL-forespørgsel for at hente prisen på skadeskategorien med det angivne kategori-id.
        String sql = "SELECT price FROM damage_category WHERE category_id = ?";

        // Udfører SQL-forespørgslen ved hjælp af JdbcTemplate-objektet og returnerer prisen som et Double-objekt.
        return template.queryForObject(sql, Double.class, category_id);
    }

}

