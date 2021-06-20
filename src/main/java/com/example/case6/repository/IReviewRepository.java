package com.example.case6.repository;


import com.example.case6.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository extends JpaRepository<Review,Long> {
    Iterable<Review> findAllByHouseHouseId(Long houseId);

}
