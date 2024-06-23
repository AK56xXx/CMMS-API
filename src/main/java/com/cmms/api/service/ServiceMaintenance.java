package com.cmms.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

        LocalDate mDate = maintenance.getMdate().toLocalDate();
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
       // return maintenanceRepository.findByDeviceEOSDateBeforeAndDeviceClientAndUserResponseAndStatus(now, client,
              //  Response.PENDING, Status.OPEN);
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
    public List<Maintenance> getMaintenancesByTechnicianAndDate(User technician, LocalDate mDate) {

        return maintenanceRepository.findByTechnicianAndMsdate(technician, mDate);
    }

    // we adjust the time of maintenance if we have multiple maintenance in the same
    // day by the same thechnician
    @Override
    public Maintenance adjustMaintenanceTimes(Maintenance newMaintenance, List<Maintenance> existingMaintenances) {
              // Initialize start time at 9:00 AM on the given mdate's date
            LocalDateTime startAt = newMaintenance.getMdate().withHour(9).withMinute(0);
            LocalDateTime endAt = startAt.plusHours(1);
            newMaintenance.setMsdate(newMaintenance.getMdate().toLocalDate());
            // Fetch all maintenances for the technician on the same mdate
            List<Maintenance> technicianMaintenances = maintenanceRepository.findByTechnicianAndMsdate(
                newMaintenance.getTechnician(), newMaintenance.getMsdate());

            // Combine existingMaintenances with technicianMaintenances to avoid duplication
            Set<Maintenance> allRelevantMaintenances = new HashSet<>(existingMaintenances);
            allRelevantMaintenances.addAll(technicianMaintenances);

            // Adjust times if there are conflicts
            boolean conflict;
            do {
                conflict = false;
                for (Maintenance existing : allRelevantMaintenances) {
                    LocalDateTime existingStart = existing.getStartAt();
                    LocalDateTime existingEnd = existing.getEndAt();

                    // If the current startAt overlaps with existing maintenance, adjust the time
                    if (startAt.isBefore(existingEnd) && endAt.isAfter(existingStart)) {
                        startAt = existingEnd.plusHours(1);
                        endAt = startAt.plusHours(1);
                        conflict = true;
                        break;
                    }
                }
            } while (conflict);

            // Set the adjusted start and end times to the new maintenance
            newMaintenance.setStartAt(startAt);
            newMaintenance.setEndAt(endAt);

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

    

    @Override
    public Maintenance addMaintenance(Maintenance maintenance) {
            // Set the maintenance date
            LocalDate mDate = maintenance.getMsdate();
            List<Maintenance> existingMaintenances = getMaintenancesByTechnicianAndDate(maintenance.getTechnician(), mDate);
            maintenance = adjustMaintenanceTimes(maintenance, existingMaintenances);
            
            // Save the maintenance object to the database
            return maintenanceRepository.save(maintenance);
    }

    @Override
    public List<Maintenance> getNoneAndInProgressMaintenances() {
          // Specify the responses you're interested in
          List<Response> responses = Arrays.asList(Response.APPROVED, Response.NONE);

          // Call the repository method
          return maintenanceRepository.findByUserResponseInAndStatus(responses, Status.IN_PROGRESS);
    }

}
