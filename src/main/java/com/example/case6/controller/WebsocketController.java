package com.example.case6.controller;

import com.example.case6.model.Review;
import com.example.case6.service.review.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class WebsocketController {
    @Autowired
    private IReviewService reviewService;
    @MessageMapping("/houses")
    @SendTo("/topic/houses/detail/{houseId}")
    public Review createNewProductUsingSocket(Review review) {
        return reviewService.save(review);
    }
}
