package com.example.case6.repository;


import com.example.case6.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IReviewRepository extends JpaRepository<Review, Long> {
    Iterable<Review> findAllByHouseHouseId(Long houseId);

    Iterable<Review> findReviewsByUserUserIdAndHouseHouseId(Long id, Long houseId);

    @Query(nativeQuery = true, value = "select house_house_id as houseId, avg(rating)  from house_rentals.review group by house_house_id order by rating desc limit 5")
     List<Long> findTop5RatingRoom();
}
