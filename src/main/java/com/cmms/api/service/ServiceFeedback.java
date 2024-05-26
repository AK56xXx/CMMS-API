package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Feedback;
import com.cmms.api.exception.NotFoundException;
import com.cmms.api.repository.FeedbackRepository;

@Service
public class ServiceFeedback implements IServiceFeedback {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback findFeedbackById(int id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    @Override
    public Feedback updateFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public void deleteFeedback(Feedback feedback) {
        feedbackRepository.delete(feedback);
    }

}
