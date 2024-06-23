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

import com.cmms.api.entity.Announcement;
import com.cmms.api.service.IServiceAnnouncement;

@RestController
@RequestMapping("/api/v1/announcement")
public class RestAnnouncementController {

    @Autowired
    IServiceAnnouncement iServiceAnnouncement;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public List<Announcement> findAllAnnouncements() {
        return iServiceAnnouncement.findAllAnnouncements();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Announcement findAnnouncementById(@PathVariable int id) {

        return iServiceAnnouncement.findAnnouncementById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public Announcement AddAnnouncement(@RequestBody Announcement announcement) {
        return iServiceAnnouncement.createAnnouncement(announcement);
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public Announcement UpdateAnnouncement(@RequestBody Announcement announcement) {
        return iServiceAnnouncement.updateAnnouncement(announcement);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public void DeleteAnnouncement(@PathVariable int id) {
        iServiceAnnouncement.deleteAnnouncement(iServiceAnnouncement.findAnnouncementById(id));
    }

}
