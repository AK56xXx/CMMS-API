package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.Device;
import com.cmms.api.entity.Ticket;
import com.cmms.api.entity.User;
import com.cmms.api.entity.enum_options.Status;

public interface IServiceTicket {

    public Ticket createTicket(Ticket ticket);

    public Ticket findTicketById(int id);

    public Ticket updateTicket(Ticket ticket);

    public List<Ticket> findAllTickets();

    public void deleteTicket(Ticket ticket);

    public List<Ticket> getByClientAndStatus(User client);

    public List<Ticket> getByStatus();

}
