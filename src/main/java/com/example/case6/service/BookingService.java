package com.example.case6.service;

import com.example.case6.model.Booking;
import com.example.case6.repository.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService<Booking> {
    @Autowired
    IBookingRepository bookingRepository;

    @Override
    public Iterable<Booking> findAll(int page, int size) {
        return null;
    }
    public Iterable<Booking> findAll() {
        return bookingRepository.findAll();
    }
    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).get();
    }

    @Override
    public List<Booking> getAllBookingByHouseId(Long id) {
        return bookingRepository.getBookingsByHouseHouseId(id);
    }
}
