package com.example.case6.repository;

import com.example.case6.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getBookingsByHouseHouseId(Long id);

    Iterable<Booking> getBookingsByUsersUserId(Long id);
    @Query(nativeQuery = true ,value = "select * from house_rentals.booking where house_id=?1 and  checkin_date <= ?2 and checkout_date >= ?2")
    Booking findBookingByHouseIdAndCurrentDateRage(Long id,Date date);

}