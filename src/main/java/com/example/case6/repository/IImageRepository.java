package com.example.case6.repository;

import com.example.case6.model.House;
import com.example.case6.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Images,Long> {

    Iterable<Images> findAllByHouse(House house);

    Iterable<Images> findImagesByHouseHouseId(Long id);

    Iterable<Images> getImagesByHouseHouseId(Long houseId);
}
