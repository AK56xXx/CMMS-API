package com.cmms.api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String version;
    private String manufacturer;
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    List<Device> device;

    @JsonIgnore
    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    List<Problem> problem;

}
