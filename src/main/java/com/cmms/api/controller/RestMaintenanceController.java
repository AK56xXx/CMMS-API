package com.cmms.api.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.Maintenance;
import com.cmms.api.entity.User;
import com.cmms.api.service.IServiceMaintenance;
import com.cmms.api.service.IServiceUser;

@RestController
@RequestMapping("/api/v1/maintenance")
public class RestMaintenanceController {

    @Autowired
    IServiceMaintenance iServiceMaintenance;

    @Autowired
    IServiceUser iServiceUser;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public List<Maintenance> findAllMaintenances() {
        return iServiceMaintenance.findAllMaintenances();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Maintenance findMaintenanceById(@PathVariable int id) {

        return iServiceMaintenance.findMaintenanceById(id);
    }

    // we need to specify in the forentend that we only choose from GET /technicians
    // when adding a technician to a maintenance
    @PutMapping("/add") // I don't know why put works but post doesn't
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addMaintenance(@RequestBody Maintenance maintenance) {

        Maintenance newMaintenance = iServiceMaintenance.createMaintenance(maintenance);
        return ResponseEntity.ok(newMaintenance);

    }

    // the update will set the non-existent json argument to null, is this normal
    // behaviour ?
    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public Maintenance UpdateMaintenance(@RequestBody Maintenance maintenance) {
        return iServiceMaintenance.updateMaintenance(maintenance);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public void DeleteMaintenance(@PathVariable int id) {
        iServiceMaintenance.deleteMaintenance(iServiceMaintenance.findMaintenanceById(id));
    }

    // Adding maintenance notification for expired devices per client

    @PostMapping("/auto-add/{clientId}")
    @PreAuthorize("isAuthenticated()")
    public void scheduleMaintenanceForExpiredDevices(@PathVariable int clientId) {
        iServiceMaintenance.insertMaintenanceForExpiredDevicesByClient(clientId);
    }

    // List of maintenances for expired devices per client (status = open,
    // user_response = pending)
    @GetMapping("/eos-notif/{clientId}")
    @PreAuthorize("isAuthenticated()")
    public List<Maintenance> getMaintenancesForExpiredDevices(@PathVariable int clientId) {
        // return iServiceMaintenance.getMaintenanceForExpiredDevicesByClient(clientId);

        return iServiceMaintenance.getOpenMaintenanceClientPending(clientId);
    }

    // get list of available technicians per date
    @GetMapping("/available-technicians/{mdate}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> availableTechnicians(@PathVariable String mdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z");
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(mdate, formatter);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format");
        }

        List<User> availableTechnicians = iServiceUser.getAvailableTechnicians(localDateTime);
        return ResponseEntity.ok(availableTechnicians);

    }

    // List of approved maintenances per client
    @GetMapping("/approved/client/{clientId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> approvedMaintenances(@PathVariable User clientId) {
        List<Maintenance> approvedMaintenances = iServiceMaintenance.getApprovedMaintenances(clientId);
        return ResponseEntity.ok(approvedMaintenances);

    }

    // List of in progress maintenances
    @GetMapping("/in-progress")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> inProgressMaintenances() {
        List<Maintenance> inProgressMaintenances = iServiceMaintenance.getInProgressMaintenances();

        return ResponseEntity.ok(inProgressMaintenances);

    }

}
