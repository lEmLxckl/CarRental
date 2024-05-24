package org.example.carrental.Service;

import org.example.carrental.Repository.DamageRepo;
import org.example.carrental.model.Damage_category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageService {
    @Autowired
    DamageRepo damageRepo;

    public List<Damage_category> fetchAllDamageCategories(){
        return damageRepo.fetchAll();
    }

    public void addDamage(Damage_category damage_category){
        damageRepo.AddDamage(damage_category);
    }

    public void updateCategory(Damage_category damage_category, int category_id){
        damageRepo.updateDamage(damage_category, category_id);
    }
    public boolean deleteDamage(int category_id){
        return damageRepo.deleteDamage(category_id);
    }

    public Damage_category findSpecifikDamage(int category_id){
        return damageRepo.findDamageByid(category_id);
    }

    public Double getSpecificDamagePrice(int category_id) {
        return damageRepo.findSpecificDamagePrice(category_id);
    }



}

