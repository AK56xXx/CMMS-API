package com.cmms.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.cmms.api.entity.User;

public interface IServiceUser {

    public User createUser(User user);

    public User findUserById(int id);

    public User updateUser(User user);

    public List<User> findAllUsers();

    public void deleteUser(User user);

    // get list of all technicians
    public List<User> getAllTechnicians();

    // get list of all clients
    public List<User> getAllClients();

    // get list of available technicians
    public List<User> getAvailableTechnicians(LocalDateTime mDate);

    public Optional<User> getUserByUsername(String username);

    public Optional<User> updateUserImage(int userId, String imageUrl);

}
