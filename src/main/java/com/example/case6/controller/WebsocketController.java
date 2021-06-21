package com.example.case6.controller;

import com.example.case6.model.Review;

import com.example.case6.service.review.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class WebsocketController {
    @Autowired
    private IReviewService reviewService;
    @MessageMapping("/houses")
    @SendTo("/topic/houses")
    public Review addReview(Review review){
        return reviewService.save(review);
    }
}
