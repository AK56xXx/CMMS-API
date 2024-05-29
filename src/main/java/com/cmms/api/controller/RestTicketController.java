package com.cmms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.Ticket;
import com.cmms.api.service.IServiceTicket;

@RestController
@RequestMapping("/api/v1/ticket")
public class RestTicketController {

    @Autowired
    IServiceTicket iServiceTicket;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public List<Ticket> findAllTickets() {
        return iServiceTicket.findAllTickets();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Ticket findTicketById(@PathVariable int id) {

        return iServiceTicket.findTicketById(id);
    }

    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public Ticket AddTicket(@RequestBody Ticket ticket) {
        return iServiceTicket.createTicket(ticket);
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public Ticket UpdateTicket(@RequestBody Ticket ticket) {
        return iServiceTicket.updateTicket(ticket);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public void DeleteTicket(@PathVariable int id) {
        iServiceTicket.deleteTicket(iServiceTicket.findTicketById(id));
    }

}
