package com.cmms.api.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Maintenance;
import com.cmms.api.entity.Role;
import com.cmms.api.entity.User;
import com.cmms.api.repository.MaintenanceRepository;
import com.cmms.api.repository.UserRepository;

@Service
public class ServiceUser implements IServiceUser {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private MaintenanceRepository maintenanceRepository;

	@SuppressWarnings("null")
	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findUserById(int id) {
		return userRepository.findById(id).get();
	}

	@SuppressWarnings("null")
	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@SuppressWarnings("null")
	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);

	}

	// find user by technician role
	@Override
	public List<User> getAllTechnicians() {
		return userRepository.findByRole(Role.TECHNICIAN);
	}

	@Override
	public List<User> getAllClients() {
		return userRepository.findByRole(Role.CLIENT);
	}

	@Override
	public List<User> getAvailableTechnicians(LocalDateTime mDate) {

		List<User> availableTechnicians = new ArrayList<>();

		// Fetch all technicians
		List<User> allTechnicians = getAllTechnicians();

		for (User technician : allTechnicians) {
			// Fetch all maintenances for the technician on the given date
			List<Maintenance> maintenances = maintenanceRepository.findByTechnicianAndMdate(technician, mDate);

			boolean isAvailable = true;

			for (Maintenance maintenance : maintenances) {
				// Check the time difference between the end of the current maintenance and the
				// entry datetime of maintenance date
				if (ChronoUnit.HOURS.between(maintenance.getEndAt(), mDate.plusHours(18)) < 2) { // (?) i think we can
																									// change it to
																									// maintenance.getMdate().plusHours(18)
					isAvailable = false;
					break;
				}
			}

			if (isAvailable) {
				availableTechnicians.add(technician);
			}
		}

		return availableTechnicians;
	}

	@Override
	public Optional <User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
