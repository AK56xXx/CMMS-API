package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Feedback;
import com.cmms.api.entity.Maintenance;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    Feedback findByMaintenance(Maintenance maintenance);

}
