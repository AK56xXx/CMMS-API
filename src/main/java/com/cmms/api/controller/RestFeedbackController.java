package com.cmms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.Feedback;
import com.cmms.api.entity.Maintenance;
import com.cmms.api.entity.enum_options.Status;
import com.cmms.api.service.IServiceFeedback;
import com.cmms.api.service.IServiceMaintenance;

@RestController
@RequestMapping("/api/v1/feedback")
public class RestFeedbackController {

    @Autowired
    IServiceFeedback iServiceFeedback;

    @Autowired
    IServiceMaintenance iServiceMaintenance;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public List<Feedback> findAllFeedbacks() {
        return iServiceFeedback.findAllFeedbacks();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Feedback findFeedbackById(@PathVariable int id) {

        return iServiceFeedback.findFeedbackById(id);
    }

    // to add a feedback we need to check if the maintenance is closed
    // check if a feedback already exists or not
    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> AddFeedback(@RequestBody Feedback feedback) {

        Maintenance maintenance = iServiceMaintenance.findMaintenanceById(feedback.getMaintenance().getId());
        if (maintenance.getStatus() != Status.CLOSED) {

            return ResponseEntity.badRequest().body("This maintenance is not closed yet");

        }

        else if (maintenance.getFeedback() != null) {

            return ResponseEntity.badRequest().body("This maintenance already has a feedback");
        }

        return ResponseEntity.ok().body(iServiceFeedback.createFeedback(feedback));
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public Feedback UpdateFeedback(@RequestBody Feedback feedback) {
        return iServiceFeedback.updateFeedback(feedback);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public void DeleteFeedback(@PathVariable int id) {
        iServiceFeedback.deleteFeedback(iServiceFeedback.findFeedbackById(id));
    }

}
