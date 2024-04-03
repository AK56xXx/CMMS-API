package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.Maintenance;

public interface IServiceMaintenance {

    public Maintenance createMaintenance(Maintenance maintenance);

    public Maintenance findMaintenanceById(int id);

    public Maintenance updateMaintenance(Maintenance maintenance);

    public List<Maintenance> findAllMaintenances();

    public void deleteMaintenance(Maintenance maintenance);

}
