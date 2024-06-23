package com.cmms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.Device;
import com.cmms.api.entity.Ticket;
import com.cmms.api.entity.User;
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

    // for admin
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public Ticket CreateTicket(@RequestBody Ticket ticket) {
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

    // get all the open tickets per client
    @GetMapping("/client/{clientId}")
    @PreAuthorize("isAuthenticated()")
    public List<Ticket> findTicketByClientAndStatus(@PathVariable User clientId) {
        return iServiceTicket.getByClientAndStatus(clientId);
    }

    // get all the open tickets (admin panel view)
    @GetMapping("/open")
    @PreAuthorize("isAuthenticated()")
    public List<Ticket> findAllOpenTickets() {
        return iServiceTicket.getByStatus();
    }

    @GetMapping("/device/{deviceId}")
    @PreAuthorize("isAuthenticated()")
    public List<Ticket> findTicketByClientAndStatus(@PathVariable Device deviceId) {
        return iServiceTicket.getByDeviceAndStatus(deviceId);
    }

}
