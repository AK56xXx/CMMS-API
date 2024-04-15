package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.Device;

public interface IServiceDevice {

    public Device createDevice(Device device);

    public Device findDeviceById(int id);

    public Device updateDevice(Device device);

    public List<Device> findAllDevices();

    public void deleteDevice(Device device);

}
