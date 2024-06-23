package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.Announcement;

public interface IServiceAnnouncement {

    public Announcement createAnnouncement(Announcement announcement);

    public Announcement findAnnouncementById(int id);

    public Announcement updateAnnouncement(Announcement announcement);

    public List<Announcement> findAllAnnouncements();

    public void deleteAnnouncement(Announcement announcement);

}
