package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Maintenance;
import com.cmms.api.exception.NotFoundException;
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
        return maintenanceRepository.findById(id).orElseThrow(() -> new NotFoundException());
        // you need exception handler
        // or it will show 401 status and trigger (NoSuchElementException: No value
        // present error typically arises in relation to the Optional return type. For
        // example, if you try to get the value of an empty Optional.)
        // in this case,we used a custom exception
        // to trigger not found status and prevent NoSuchElementException.
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
