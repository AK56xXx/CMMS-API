package com.cmms.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Ticket;
import com.cmms.api.entity.User;
import com.cmms.api.entity.enum_options.Status;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByClientAndStatus(User client, Status status);
    

}
