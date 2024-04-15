package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Device;
import com.cmms.api.repository.DeviceRepository;

@Service
public class ServiceDevice implements IServiceDevice {

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device findDeviceById(int id) {
        return deviceRepository.findById(id).get();
    }

    @Override
    public Device updateDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public List<Device> findAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public void deleteDevice(Device device) {
        deviceRepository.delete(device);
    }

}
