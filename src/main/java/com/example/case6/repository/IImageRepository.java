package com.example.case6.repository;

import com.example.case6.model.House;
import com.example.case6.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface IImageRepository extends JpaRepository<Images,Long> {
    Iterable<Images> getImagesByHouseHouseId(Long houseId);
    Iterable<Images> findAllByHouse(House house);

@Query(value = "SELECT u.linkImage FROM Images u where u.house.houseId =:id")
Iterable<Images> findImagesByHouseHouseId(@Param("id") Long id);

    }
