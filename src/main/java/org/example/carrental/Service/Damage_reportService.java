package org.example.carrental.Service;

import org.example.carrental.model.Damage_report;
import org.example.carrental.Repository.Damage_reportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Damage_reportService {
    @Autowired
    Damage_reportRepo damage_reportRepo;

    public List<Damage_report> showReport() {
        return damage_reportRepo.fetchAll();
    }
    public void addDamage_report(Damage_report damage_report){
        damage_reportRepo.CreateDamage_report(damage_report);
    }

    public void updateReport(Damage_report damageReport, int report_id){
        damage_reportRepo.updateDamageReport(damageReport, report_id);
    }

    public Damage_report findSpecifikReport(int report_id){
        return damage_reportRepo.findDamageReportByid(report_id);
    }
    public boolean deleteReport(int report_id){
        return damage_reportRepo.deleteReport(report_id);
    }

}