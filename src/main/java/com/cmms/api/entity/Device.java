package com.cmms.api.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String serial;
    private String description;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "eos_date")
    private LocalDateTime EOSDate; // end of service for major components (exp: purchase_date.plusYears(1))

    @Column(columnDefinition = "int default 0")
    private int repair_nbr;

    @ManyToOne
    // @JoinColumn(name = "client_id", referencedColumnName = "id")
    private User client;

    @ManyToOne
    // @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model;


    @JsonIgnore
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    List<Ticket> ticket;

}
