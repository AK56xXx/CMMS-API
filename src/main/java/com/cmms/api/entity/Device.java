package com.cmms.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private String ip_address;
    private String mac_address;
    private LocalDateTime purchase_date;
    private LocalDateTime EOS_date; // end of service for major components (exp: purchase_date.plusYears(1))

    @Column(columnDefinition = "int default 0")
    private int repair_nbr;

    @ManyToOne
    private User client;

    @ManyToOne
    private Model model;

}
