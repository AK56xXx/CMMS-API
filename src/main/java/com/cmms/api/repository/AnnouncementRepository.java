package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

}
