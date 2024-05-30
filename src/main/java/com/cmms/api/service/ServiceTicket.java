package com.cmms.api.service;

import java.util.List;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Device;
import com.cmms.api.entity.Ticket;
import com.cmms.api.entity.User;
import com.cmms.api.entity.enum_options.Status;
import com.cmms.api.exception.NotFoundException;
import com.cmms.api.repository.TicketRepository;

@Service
public class ServiceTicket implements IServiceTicket {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket createTicket(Ticket ticket) {

        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket findTicketById(int id) {

        return ticketRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {

        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findAllTickets() {

        return ticketRepository.findAll();
    }

    @Override
    public void deleteTicket(Ticket ticket) {

        ticketRepository.delete(ticket);
    }

    // get all the open tickets per client
    @Override
    public List<Ticket> getByClientAndStatus(User client) {

        return ticketRepository.findByClientAndStatus(client, Status.OPEN);
    }

    // get all the open tickets (admin panel view)
    @Override
    public List<Ticket> getByStatus() {

        return ticketRepository.findByStatus(Status.OPEN);
    }

}
