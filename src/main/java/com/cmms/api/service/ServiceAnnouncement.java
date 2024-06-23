package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Announcement;
import com.cmms.api.repository.AnnouncementRepository;

@Service
public class ServiceAnnouncement implements IServiceAnnouncement {

    @Autowired
    AnnouncementRepository announcementRepository;

    @Override
    public Announcement createAnnouncement(Announcement announcement) {

        return announcementRepository.save(announcement);

    }

    @Override
    public Announcement updateAnnouncement(Announcement announcement) {

        return announcementRepository.save(announcement);
    }

    @Override
    public Announcement findAnnouncementById(int id) {

        return announcementRepository.findById(id).get();
    }

    @Override
    public List<Announcement> findAllAnnouncements() {

        return announcementRepository.findAll();
    }

    @Override
    public void deleteAnnouncement(Announcement announcement) {

        announcementRepository.delete(announcement);
    }

}
