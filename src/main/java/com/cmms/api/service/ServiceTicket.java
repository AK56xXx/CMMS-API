package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Ticket;
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

}
