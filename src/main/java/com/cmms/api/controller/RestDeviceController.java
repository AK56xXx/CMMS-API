package com.cmms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.Device;
import com.cmms.api.service.IServiceDevice;

@RestController
@RequestMapping("/api/v1/device")
public class RestDeviceController {

    @Autowired
    IServiceDevice iServiceDevice;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public List<Device> findAllDevices() {
        return iServiceDevice.findAllDevices();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT','TECHNICIAN')")
    public ResponseEntity<?> findDeviceById(@PathVariable int id) {

        Device device = iServiceDevice.findDeviceById(id);

        return ResponseEntity.ok(device);

    }

    @PutMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT','TECHNICIAN')")
    public ResponseEntity<?> AddDevice(@RequestBody Device device) {

        device.setIpAddress("0.0.0.0");

        Device createDevice = iServiceDevice.createDevice(device);

        return ResponseEntity.status(HttpStatus.CREATED).body(createDevice);

    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public Device UpdateDevice(@RequestBody Device device) {
        return iServiceDevice.updateDevice(device);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public void DeleteDevice(@PathVariable int id) {
        iServiceDevice.deleteDevice(iServiceDevice.findDeviceById(id));
    }

}
