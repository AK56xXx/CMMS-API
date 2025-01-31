package com.cmms.api.service;

import java.time.LocalDate;
import java.util.List;
import com.cmms.api.entity.Maintenance;
import com.cmms.api.entity.User;

public interface IServiceMaintenance {

    public Maintenance createMaintenance(Maintenance maintenance);

    public Maintenance findMaintenanceById(int id);

    public Maintenance updateMaintenance(Maintenance maintenance);

    public List<Maintenance> findAllMaintenances();

    public void deleteMaintenance(Maintenance maintenance);

    public void insertMaintenanceForExpiredDevicesByClient(int clientId);

    public List<Maintenance> getMaintenanceForExpiredDevicesByClient(int clientId);

    public List<Maintenance> getOpenMaintenanceClientPending(int clientId);

    public List<Maintenance> getMaintenancesByTechnicianAndDate(User technician, LocalDate mDate);

    public Maintenance adjustMaintenanceTimes(Maintenance newMaintenance, List<Maintenance> existingMaintenances);

    // get the maintenance list per client when response = approved
    public List<Maintenance> getApprovedMaintenances(User client);

    // get the maintenance list when status = in progress
    public List<Maintenance> getInProgressMaintenances();

    //testing add for react js
    public Maintenance addMaintenance(Maintenance maintenance);

    // get the maintenance list when status = in progress or NONE (for ADMIN)
    public List<Maintenance> getNoneAndInProgressMaintenances();

}
