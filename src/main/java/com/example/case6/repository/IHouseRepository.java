package com.example.case6.repository;

import com.example.case6.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface IHouseRepository extends JpaRepository<House,Long> {
    @Query(nativeQuery = true,value = "select * from house \n" +
            "                where (house_address like '%'+?1+'%' or house_name like \"%h%\") and house_id NOT IN (\n" +
            "                SELECT DISTINCT house_id\n" +
            "                FROM booking\n" +
            "                WHERE checkout_date >= ?2 AND checkin_date <= ?3)")
    Iterable<House> findHouse(String search,Date checkin, Date checkout);
}
