package com.cmms.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.cmms.api.entity.enum_options.MaintenanceType;
import com.cmms.api.entity.enum_options.Response;
import com.cmms.api.entity.enum_options.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)

public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;

    @Column(name = "notif_msg")
    private String notifMsg;

    @Column(name = "m_date")
    private LocalDateTime mdate;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    // manitenance status
    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "ENUM('OPEN', 'IN_PROGRESS', 'CLOSED') DEFAULT 'OPEN'")
    private Status status;

    // user response
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_response", columnDefinition = "ENUM('PENDING', 'APPROVED', 'REJECTED', 'NONE') DEFAULT 'PENDING'")
    private Response userResponse;

    // maintenance type
    @Enumerated(value = EnumType.STRING)
    @Column(name = "maintenance_type", columnDefinition = "ENUM('AUTO', 'MANUAL', 'TICKET')")
    private MaintenanceType maintenanceType;

    @CreatedDate
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @ManyToOne
    private User technician;

    @ManyToOne
    private Device device;

    @OneToOne
    @JoinColumn(name = "ticket")
    private Ticket ticket;

    @JsonIgnore
    @OneToOne(mappedBy = "maintenance", cascade = CascadeType.ALL)
    private Feedback feedback;
}
