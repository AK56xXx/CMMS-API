package com.cmms.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Device;
import com.cmms.api.entity.Maintenance;
import com.cmms.api.entity.Role;
import com.cmms.api.entity.User;
import com.cmms.api.entity.enum_options.MaintenanceType;
import com.cmms.api.entity.enum_options.Response;
import com.cmms.api.entity.enum_options.Status;
import com.cmms.api.exception.NotFoundException;
import com.cmms.api.repository.DeviceRepository;
import com.cmms.api.repository.MaintenanceRepository;
import com.cmms.api.repository.UserRepository;

@Service
public class ServiceMaintenance implements IServiceMaintenance {

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;

    // using the basic add maintenance for the automated maintenance
    @SuppressWarnings("null")
    @Override
    public Maintenance createMaintenance(Maintenance maintenance) {
        // return maintenanceRepository.save(maintenance);

        LocalDateTime mDate = maintenance.getMdate();
        User technician = maintenance.getTechnician();

        // Fetch existing maintenances for the technician on the same date
        List<Maintenance> existingMaintenances = getMaintenancesByTechnicianAndDate(technician, mDate);

        // Adjust the maintenance times if needed
        maintenance = adjustMaintenanceTimes(maintenance, existingMaintenances);

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

    // insert maintenance for expired devices per client (multiple)
    @Override
    public void insertMaintenanceForExpiredDevicesByClient(int clientId) {
        LocalDateTime now = LocalDateTime.now();
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));
        List<Device> expiredDevices = deviceRepository.findByEOSDateBeforeAndClient(now, client);

        for (Device device : expiredDevices) {
            // Check if there is already a maintenance record for this device
            boolean exists = maintenanceRepository.existsByDevice(device);

            if (!exists) {
                // Create a new maintenance record if it doesn't exist
                Maintenance maintenance = new Maintenance();
                maintenance.setClient(client);
                maintenance.setTitle("Maintenance for " + device.getName() + device.getId());
                maintenance.setDescription("Scheduled maintenance due to EOS date");
                maintenance.setStatus(Status.OPEN);
                maintenance.setUserResponse(Response.PENDING);
                maintenance.setMaintenanceType(MaintenanceType.AUTO);
                maintenance.setDevice(device);
                maintenanceRepository.save(maintenance);

            }

        }
    }

    // get the maintenance list for expired devices per client
    @Override
    public List<Maintenance> getMaintenanceForExpiredDevicesByClient(int clientId) {
        LocalDateTime now = LocalDateTime.now();
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));
        return maintenanceRepository.findByDeviceEOSDateBeforeAndDeviceClient(now, client);
    }

    // get the maintenance list for expired devices per user when status = open and
    // user_response = pending
    @Override
    public List<Maintenance> getOpenMaintenanceClientPending(int clientId) {
        LocalDateTime now = LocalDateTime.now();
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));

        return maintenanceRepository.findByDeviceEOSDateBeforeAndDeviceClientAndUserResponseAndStatus(
                now, client, Response.PENDING, Status.OPEN);
    }

    // get the maintenance list by technician and date
    @Override
    public List<Maintenance> getMaintenancesByTechnicianAndDate(User technician, LocalDateTime mDate) {

        return maintenanceRepository.findByTechnicianAndMdate(technician, mDate);
    }

    // we adjust the time of maintenance if we have multiple maintenance in the same
    // day by the same thechnician
    @Override
    public Maintenance adjustMaintenanceTimes(Maintenance newMaintenance, List<Maintenance> existingMaintenances) {
        // LocalDateTime startAt = newMaintenance.getStartAt();
        LocalDateTime startAt = newMaintenance.getMdate().withHour(9).withMinute(0);
        LocalDateTime endAt = newMaintenance.getEndAt();

        // Check if start_at is after 9 AM
        // if (startAt.toLocalTime().isAfter(LocalTime.of(9, 0))) {
        // Find the latest endAt time among the existing maintenances
        LocalDateTime latestEndAt = existingMaintenances.stream()
                .map(Maintenance::getEndAt)
                .max(LocalDateTime::compareTo)
                .orElse(startAt);

        // Set start_at to latestEndAt + 1 hour
        startAt = latestEndAt.plusHours(1);
        // Set end_at to start_at + 1 hour
        endAt = startAt.plusHours(1);

        newMaintenance.setStartAt(startAt);
        newMaintenance.setEndAt(endAt);
        // }

        return newMaintenance;

    }

    // get the maintenance list per client when response = approved
    @Override
    public List<Maintenance> getApprovedMaintenances(User client) {

        client = userRepository.findById(client.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));

        if (!client.getRole().equals(Role.CLIENT)) {
            throw new NotFoundException();
        }

        return maintenanceRepository.findByClientAndUserResponse(client, Response.APPROVED);

    }

    // get the maintenance list where status = in progress and user_response =
    // approved
    @Override
    public List<Maintenance> getInProgressMaintenances() {

        return maintenanceRepository.findByUserResponseAndStatus(Response.APPROVED, Status.IN_PROGRESS);

    }

}
