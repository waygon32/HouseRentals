package com.example.case6.repository;

import com.example.case6.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking,Long> {
 List<Booking> getBookingsByHouseHouseId(Long id);
 Iterable<Booking> getBookingsByUsersUserId(Long id);
}
