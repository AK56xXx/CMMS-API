package com.cmms.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Device;
import com.cmms.api.entity.Maintenance;
import com.cmms.api.entity.User;
import com.cmms.api.entity.enum_options.Response;
import com.cmms.api.entity.enum_options.Status;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    // (*) The name of the method need to be the same as in the models attributes
    // that we use in the method parameters
    // this method is used to get the list of maintenances of the EOS devices per
    // user
    List<Maintenance> findByDeviceEOSDateBeforeAndDeviceClient(LocalDateTime date, User client);

    // find devices that have maintenances with specific status ( status = open,
    // user_response = pending)
    List<Maintenance> findByDeviceEOSDateBeforeAndDeviceClientAndUserResponseAndStatus(
            LocalDateTime eosDate, User client, Response userResponse, Status status);

    // find devices that have maintenances
    Optional<Maintenance> findByDevice(Device device);

}
