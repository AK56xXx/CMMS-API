package com.cmms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.Problem;
import com.cmms.api.service.IServiceProblem;

@RestController
@RequestMapping("/api/v1/problem")
public class RestProblemController {

    @Autowired
    IServiceProblem iServiceProblem;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public List<Problem> findAllProblems() {
        return iServiceProblem.findAllProblems();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Problem findProblemById(@PathVariable int id) {

        return iServiceProblem.findProblemById(id);
    }

    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public Problem AddProblem(@RequestBody Problem problem) {
        return iServiceProblem.createProblem(problem);
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public Problem UpdateProblem(@RequestBody Problem problem) {
        return iServiceProblem.updateProblem(problem);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public void DeleteProblem(@PathVariable int id) {
        iServiceProblem.deleteProblem(iServiceProblem.findProblemById(id));
    }

}
