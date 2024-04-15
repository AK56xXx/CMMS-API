package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.Ticket;

public interface IServiceTicket {

    public Ticket createTicket(Ticket ticket);

    public Ticket findTicketById(int id);

    public Ticket updateTicket(Ticket ticket);

    public List<Ticket> findAllTickets();

    public void deleteTicket(Ticket ticket);

}
