package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cmms.api.entity.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

}
