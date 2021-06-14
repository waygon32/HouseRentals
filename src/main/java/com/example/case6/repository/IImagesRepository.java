package com.example.case6.repository;

import com.example.case6.model.House;
import com.example.case6.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagesRepository  extends JpaRepository<Images,Long> {
//    Iterable<Images> getImagesByHouseId(Long houseId);
    Iterable<Images> getImagesByHouseId(Long houseId);
}
