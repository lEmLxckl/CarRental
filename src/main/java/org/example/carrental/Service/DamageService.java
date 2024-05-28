package org.example.carrental.Service;

import org.example.carrental.model.Damage_category;
import org.example.carrental.Repository.DamageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageService {
    @Autowired
    DamageRepo damageRepo;

    public List<Damage_category> getAllDamageCategories() {
        return damageRepo.getAllDamageCategories();
    }

    public void addDamage(Damage_category d) {
        damageRepo.AddDamage(d);
    }

    public void updateCategory(Damage_category damageCategory) {
        damageRepo.updateDamage(damageCategory);
    }

    public Boolean deleteDamage(Damage_category category_id) {
        return damageRepo.deleteDamage(category_id);
    }

    public Damage_category findExactDamage(int category_id) {
        return damageRepo.findDamageByid(category_id);
    }

    public Double getDamagePrice(int category_id) {
        return damageRepo.getDamagePrice(category_id);
    }
}
