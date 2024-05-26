package com.cmms.api.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.Maintenance;
import com.cmms.api.entity.enum_options.Response;
import com.cmms.api.entity.enum_options.Status;
import com.cmms.api.exception.maintenance.InvalidDateException;
import com.cmms.api.security.AuthenticationResponse;
import com.cmms.api.service.IServiceMaintenance;
import com.cmms.api.service.ServiceMaintenance;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/maintenance")
public class RestMaintenanceController {

    @Autowired
    IServiceMaintenance iServiceMaintenance;

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

        if (maintenance.getM_date().isAfter(LocalDateTime.now())) {

            maintenance.setStart_at(maintenance.getM_date().withHour(9).withMinute(0));
            maintenance.setEnd_at(maintenance.getStart_at().plusHours(1));
            maintenance.setStatus(Status.IN_PROGRESS);
            maintenance.setUser_response(Response.APPROVED);

            Maintenance createdMaintenance = iServiceMaintenance.createMaintenance(maintenance);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMaintenance);

        }

        return ResponseEntity.badRequest().body("Invalid date");

    }

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

    /******** ********/
    public boolean verifyDate(Maintenance maintenance) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startAt = maintenance.getStart_at();
        LocalDateTime endAt = maintenance.getEnd_at();
        int startHour = startAt.getHour();
        int endHour = endAt.getHour();

        if (startAt.isBefore(now) || endAt.isBefore(startAt) || startHour < 9 || endHour > 18) {
            return false;
        }

        return true;
    }

}
