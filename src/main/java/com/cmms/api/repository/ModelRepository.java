package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Model;

public interface ModelRepository extends JpaRepository<Model, Integer> {

}
