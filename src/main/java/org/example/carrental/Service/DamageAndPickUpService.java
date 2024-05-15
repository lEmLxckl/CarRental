package org.example.carrental.Service;

import org.example.carrental.Repository.DamageAndPickUpRepo;
import org.example.carrental.model.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageAndPickUpService {
        private final DamageAndPickUpRepo damageAndPickUpRepo;

        @Autowired
        public DamageAndPickUpService(DamageAndPickUpRepo damageAndPickUpRepo) {
            this.damageAndPickUpRepo = damageAndPickUpRepo;
        }

        public void saveDamageAndPickUp(DamageReport damageReport) {
            damageAndPickUpRepo.saveDamageAndPickUp(damageReport);
        }
        public List<DamageReport> showAllDamageAndPickUps() {
            return damageAndPickUpRepo.showAllDamageAndPickUps();
        }

        public DamageReport getDamageAndPickUp(int id) {
            return damageAndPickUpRepo.getDamageAndPickUp(id);
        }

        public void updateDamageAndPickUp(DamageReport damageReport) {
             damageAndPickUpRepo.updateDamageAndPickUp(damageReport);
        }

        public void deleteDamageAndPickUp(int id) {
            damageAndPickUpRepo.deleteDamageAndPickUp(id);
        }
    }

