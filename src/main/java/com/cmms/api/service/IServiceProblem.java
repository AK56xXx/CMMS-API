package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.Problem;

public interface IServiceProblem {

    public Problem createProblem(Problem problem);

    public Problem findProblemById(int id);

    public Problem updateProblem(Problem problem);

    public List<Problem> findAllProblems();

    public void deleteProblem(Problem problem);

}
