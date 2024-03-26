package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.User;

public interface IServiceUser {

	public User createUser(User user);

    public User findUserById(int id);

    public User updateUser(User user);

    public List<User> findAllUsers();

    public void deleteUser(User user);


}
