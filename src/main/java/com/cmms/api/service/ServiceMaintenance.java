package com.cmms.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Device;
import com.cmms.api.entity.Maintenance;
import com.cmms.api.entity.User;
import com.cmms.api.entity.enum_options.MaintenanceType;
import com.cmms.api.entity.enum_options.Response;
import com.cmms.api.entity.enum_options.Status;
import com.cmms.api.exception.NotFoundException;
import com.cmms.api.exception.maintenance.MaintenanceAlreadyExistsException;
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

    // insert maintenance for expired devices per client (multiple)
    @Override
    public void insertMaintenanceForExpiredDevicesByClient(int clientId) {
        LocalDateTime now = LocalDateTime.now();
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));
        List<Device> expiredDevices = deviceRepository.findByEOSDateBeforeAndClient(now, client);

        for (Device device : expiredDevices) {
            // check if the device already has maintenance or not, if not, create one
            if (maintenanceRepository.findByDevice(device).isPresent()) {
                throw new MaintenanceAlreadyExistsException();

            } else {
                Maintenance maintenance = new Maintenance();
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

}
