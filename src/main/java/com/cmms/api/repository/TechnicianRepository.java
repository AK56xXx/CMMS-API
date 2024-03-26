package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Technician;

public interface TechnicianRepository extends JpaRepository<Technician, Integer> {

}
