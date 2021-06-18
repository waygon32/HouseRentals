package com.example.case6.controller;

import com.example.case6.model.Booking;
import com.example.case6.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody Booking booking, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
//        }
        bookingService.save(booking);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    //lấy ra list booking của 1 người,tự động đổi trạng thái bookingStatus trước ngày nhận phòng  1 ngày
    public ResponseEntity<?> getAllBookingByUserId(@PathVariable("id") Long id) {
        LocalDate today = LocalDate.now();
        Date currentDate = java.sql.Date.valueOf(today.plusDays(1));
        List<Booking> list = (List<Booking>) bookingService.getAllBookingByUser(id);

        for (Booking booking : list) {
            if (currentDate.compareTo(booking.getCheckinDate()) == 0) {
                booking.setBookingStatus(0);
                bookingService.save(booking);
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        bookingService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/house/{id}")
    //Lấy ra listBooking của 1 ngôi nhà
    //Để lọc ra những ngày đã có người đặt phòng
    public ResponseEntity<?> getListBookingByHouseId(@PathVariable("id") Long id) {
        List<Booking> bookingList = bookingService.getBookingsByHouseHouseId(id);
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

} 


