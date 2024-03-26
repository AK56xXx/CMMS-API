package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>{

}
