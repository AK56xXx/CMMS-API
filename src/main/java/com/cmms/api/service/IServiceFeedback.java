package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.Feedback;

public interface IServiceFeedback {

    public Feedback createFeedback(Feedback feedback);

    public Feedback findFeedbackById(int id);

    public Feedback updateFeedback(Feedback feedback);

    public List<Feedback> findAllFeedbacks();

    public void deleteFeedback(Feedback feedback);

}
