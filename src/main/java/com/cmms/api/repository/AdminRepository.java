package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
