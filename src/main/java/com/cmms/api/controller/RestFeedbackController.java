package com.cmms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.Feedback;
import com.cmms.api.service.IServiceFeedback;

@RestController
@RequestMapping("/api/v1/feedback")
public class RestFeedbackController {

    @Autowired
    IServiceFeedback iServiceFeedback;

    @GetMapping("")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<Feedback> findAllFeedbacks() {
        return iServiceFeedback.findAllFeedbacks();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public Feedback findFeedbackById(@PathVariable int id) {

        return iServiceFeedback.findFeedbackById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Feedback AddFeedback(@RequestBody Feedback feedback) {
        return iServiceFeedback.createFeedback(feedback);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Feedback UpdateFeedback(@RequestBody Feedback feedback) {
        return iServiceFeedback.updateFeedback(feedback);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void DeleteFeedback(@PathVariable int id) {
        iServiceFeedback.deleteFeedback(iServiceFeedback.findFeedbackById(id));
    }

}
