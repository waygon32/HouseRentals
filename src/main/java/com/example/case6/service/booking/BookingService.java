package com.example.case6.service.booking;

import com.example.case6.model.Booking;
import com.example.case6.repository.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {
    @Autowired
    IBookingRepository bookingRepository;


    public Iterable<Booking> findAll() {
        return bookingRepository.findAll();
    }


    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void remove(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }


    @Override
    public List<Booking> getBookingsByHouseHouseId(Long id) {
        return bookingRepository.getBookingsByHouseHouseId(id);
    }

    @Override
    public Iterable<Booking> getAllBookingByUser(Long id) {
        return bookingRepository.getBookingsByUsersUserId(id);
    }

    public Booking findBookingHouseIdAndCurrentDate(Long houseId, Date currentDate) {
        return bookingRepository.findBookingByHouseIdAndCurrentDateRage(houseId, currentDate);
    }

    public List<Booking> setBookingStatusByCurrentDate(Date date) {
        return bookingRepository.getListBookingHaveDone(date);
    }

    public List<Booking> getAllBookingsByBookingStatus(Integer status, Long userId) {
        return bookingRepository.getBookingsByBookingStatusAndUsersUserId(status, userId);
    }

    public String getTotalTurnOverPerMonth(Long houseId, int month, int years) {
        return bookingRepository.getTotalTurnOverPerMonth(houseId, month, years);
    }
}
