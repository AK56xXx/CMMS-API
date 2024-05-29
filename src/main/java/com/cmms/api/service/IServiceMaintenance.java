package com.cmms.api.service;

import java.time.LocalDateTime;
import java.util.List;

import com.cmms.api.entity.Maintenance;
import com.cmms.api.entity.User;
import com.cmms.api.entity.enum_options.Response;

public interface IServiceMaintenance {

    public Maintenance createMaintenance(Maintenance maintenance);

    public Maintenance findMaintenanceById(int id);

    public Maintenance updateMaintenance(Maintenance maintenance);

    public List<Maintenance> findAllMaintenances();

    public void deleteMaintenance(Maintenance maintenance);

    public void insertMaintenanceForExpiredDevicesByClient(int clientId);

    public List<Maintenance> getMaintenanceForExpiredDevicesByClient(int clientId);

    public List<Maintenance> getOpenMaintenanceClientPending(int clientId);

    public List<Maintenance> getMaintenancesByTechnicianAndDate(User technician, LocalDateTime mDate);

    public Maintenance adjustMaintenanceTimes(Maintenance newMaintenance, List<Maintenance> existingMaintenances);

    // get the maintenance list per client when response = approved
    public List<Maintenance> getApprovedMaintenances(User client);

    // get the maintenance list when status = in progress
    public List<Maintenance> getInProgressMaintenances();

}
