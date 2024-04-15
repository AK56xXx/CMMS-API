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

import com.cmms.api.entity.Device;
import com.cmms.api.service.IServiceDevice;



@RestController
@RequestMapping("/api/v1/device")
public class RestDeviceController {

    @Autowired
    IServiceDevice iServiceDevice;

    @GetMapping("")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<Device> findAllDevices() {
        return iServiceDevice.findAllDevices();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public Device findDeviceById(@PathVariable int id) {

        return iServiceDevice.findDeviceById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Device AddDevice(@RequestBody Device device) {
        return iServiceDevice.createDevice(device);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Device UpdateDevice(@RequestBody Device device) {
        return iServiceDevice.updateDevice(device);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void DeleteDevice(@PathVariable int id) {
        iServiceDevice.deleteDevice(iServiceDevice.findDeviceById(id));
    }

}
