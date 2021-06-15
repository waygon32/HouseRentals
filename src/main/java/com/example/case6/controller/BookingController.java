package com.example.case6.controller;

import com.example.case6.model.Booking;
import com.example.case6.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.InvocableHandlerMethod;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody Booking booking, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        bookingService.save(booking);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    //lấy ra list booking của 1 người,tự động đổi trạng thái bookingStatus trước ngày nhận phòng  1 ngày
    public ResponseEntity<?> getAllBookingByUserId(){
        LocalDate today = LocalDate.now();
        LocalDate currentDate = today.plusDays(1);
        List<Booking> list = (List<Booking>) bookingService.findAll();
        for (Booking booking : list) {
            if (booking.getCheckinDate().toString().compareTo(currentDate.toString()) == 0) {
                booking.setBookingStatus(0);
                bookingService.save(booking);
            }
        }
        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        bookingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/house/{id}")
    //Lấy ra listBooking của 1 ngôi nhà
    //Để lọc ra những ngày đã có người đặt phòng
    public ResponseEntity<?> getListBookingByHouseId(@PathVariable("id") Long id) {
        List<Booking> bookingList = bookingService.getAllBookingByHouseId(id);
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

} 


