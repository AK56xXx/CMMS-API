package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

}
