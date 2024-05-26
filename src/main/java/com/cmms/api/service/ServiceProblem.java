package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Problem;
import com.cmms.api.exception.NotFoundException;
import com.cmms.api.repository.ProblemRepository;

@Service
public class ServiceProblem implements IServiceProblem {

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    public Problem createProblem(Problem problem) {

        return problemRepository.save(problem);
    }

    @Override
    public Problem findProblemById(int id) {

        return problemRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    @Override
    public Problem updateProblem(Problem problem) {

        return problemRepository.save(problem);
    }

    @Override
    public List<Problem> findAllProblems() {

        return problemRepository.findAll();
    }

    @Override
    public void deleteProblem(Problem problem) {

        problemRepository.delete(problem);
    }

}
