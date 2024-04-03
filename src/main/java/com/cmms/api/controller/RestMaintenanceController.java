package com.cmms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.cmms.api.service.IServiceMaintenance;
import com.cmms.api.service.ServiceMaintenance;

@RestController
@RequestMapping("/api/v1/maintenance")
public class RestMaintenanceController {

    @Autowired
    IServiceMaintenance iServiceMaintenance;

    @GetMapping("")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<Maintenance> findAllMaintenances() {
        return iServiceMaintenance.findAllMaintenances();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public Maintenance findMaintenanceById(@PathVariable int id) {

        return iServiceMaintenance.findMaintenanceById(id);
    }

    // we need to specify in the forentend that we only choose from GET /technicians
    // when adding a technician to a maintenance
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Maintenance AddMaintenance(@RequestBody Maintenance maintenance) {
        return iServiceMaintenance.createMaintenance(maintenance);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Maintenance UpdateMaintenance(@RequestBody Maintenance maintenance) {
        return iServiceMaintenance.updateMaintenance(maintenance);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void DeleteMaintenance(@PathVariable int id) {
        iServiceMaintenance.deleteMaintenance(iServiceMaintenance.findMaintenanceById(id));
    }

}
