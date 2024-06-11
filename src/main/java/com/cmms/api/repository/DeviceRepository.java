package com.cmms.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmms.api.entity.Device;
import com.cmms.api.entity.User;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    // (*) The name of the method need to be the same as in the models attributes
    // that we use in the method parameters
    // select EOS devices per client
    List<Device> findByEOSDateBeforeAndClient(LocalDateTime date, User client);

    List<Device> findByClient(User client);

}
