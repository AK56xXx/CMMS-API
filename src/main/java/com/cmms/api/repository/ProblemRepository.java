package com.cmms.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmms.api.entity.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {

}
