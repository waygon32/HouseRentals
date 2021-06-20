package com.example.case6.service.review;

import com.example.case6.model.House;
import com.example.case6.model.Review;
import com.example.case6.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private IReviewRepository reviewRepository;

    @Override
    public Iterable<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void remove(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Iterable<Review> findAllByHouseHouseId(Long houseId) {
        return reviewRepository.findAllByHouseHouseId(houseId);
    }




}
