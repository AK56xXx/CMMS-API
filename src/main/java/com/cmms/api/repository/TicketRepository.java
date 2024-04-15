package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
