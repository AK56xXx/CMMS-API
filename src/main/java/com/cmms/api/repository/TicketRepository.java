package com.cmms.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Device;
import com.cmms.api.entity.Ticket;
import com.cmms.api.entity.User;
import com.cmms.api.entity.enum_options.Status;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // get all open tickets per client
    List<Ticket> findByClientAndStatus(User client, Status status);

    // get all open tickets (admin panel view)
    List<Ticket> findByStatus(Status status);

    List<Ticket> findByDeviceAndStatus(Device device, Status status);

}
