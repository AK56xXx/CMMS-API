package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Maintenance;
import com.cmms.api.repository.MaintenanceRepository;

@Service
public class ServiceMaintenance implements IServiceMaintenance {

    @Autowired
    MaintenanceRepository maintenanceRepository;
    
    @SuppressWarnings("null")
    @Override
    public Maintenance createMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findMaintenanceById(int id) {
        return maintenanceRepository.findById(id).get();
    }

    @SuppressWarnings("null")
    @Override
    public Maintenance updateMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public List<Maintenance> findAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @SuppressWarnings("null")
    @Override
    public void deleteMaintenance(Maintenance maintenance) {
        maintenanceRepository.delete(maintenance);
    }
    

}
