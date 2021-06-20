package com.example.case6.service.review;

import com.example.case6.model.House;
import com.example.case6.model.Review;
import com.example.case6.service.IGeneralService;

public interface IReviewService extends IGeneralService<Review> {
    Iterable<Review> findAllByHouseHouseId(Long houseId);

}
