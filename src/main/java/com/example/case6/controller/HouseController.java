package com.example.case6.controller;

import com.example.case6.model.*;

import com.example.case6.repository.IImageRepository;
import com.example.case6.service.booking.BookingService;
import com.example.case6.service.booking.IBookingService;
import com.example.case6.service.house.IHouseService;
import com.example.case6.service.image.IImageService;
import com.example.case6.service.review.IReviewService;
import com.example.case6.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin("*")
@RequestMapping("/houses")
public class HouseController {
    //    convert value string to date
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    //    get UserCurrent
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @Autowired
    private IHouseService houseService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IImageRepository imageRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private IReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> findAllHouse() {
        List<House> list = (List<House>) houseService.findAll();
        LocalDate localDate = LocalDate.now();
        Date today = java.sql.Date.valueOf(localDate);

        setHouseStatusByCurrentDateAndBooking(list, today);
        setBookingStatusByCurrentDate(today);
        return new ResponseEntity<>(houseService.findAll(), HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<House> createHouse(@RequestBody House house) {
        Users userCurrent = userService.findByUsername(getPrincipal());
        house.setUsers(userCurrent);
        return new ResponseEntity<>(houseService.save(house), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> getHouse(@PathVariable Long id) {
        Optional<House> productOptional = houseService.findById(id);
        return productOptional.map(house -> new ResponseEntity<>(house, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<House> updateProduct(@PathVariable Long id, @RequestBody House house) {
        Optional<House> productOptional = houseService.findById(id);
        return productOptional.map(house1 -> {
            house.setHouseId(house1.getHouseId());
            houseService.save(house);
            return new ResponseEntity<>(house, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<House> deleteProduct(@PathVariable Long id) {
        Optional<House> productOptional = houseService.findById(id);
        return productOptional.map(product -> {
            houseService.remove(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("search")
    public ResponseEntity<Iterable<House>> searchHouse(@RequestParam String search, @RequestParam Date checkin, @RequestParam Date checkout) {
        Iterable<House> houses = houseService.findHouse(search, checkin, checkout);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }


    @GetMapping("/myHouses/{userId}")
    public ResponseEntity<Iterable<House>> getListHouseOfUserId(@PathVariable("userId") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<House> list = (List<House>) houseService.getListHouseOfUser(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<?> updateHouse(@PathVariable("id") Long id, @PathVariable("status") String status) {
        House house = houseService.findById(id).get();
        house.setHouseStatus(status);
        houseService.save(house);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<House> getProductDetail(@PathVariable Long id) {
        Optional<House> houseResult = houseService.findById(id);
        Iterable<Images> images = imageRepository.findImagesByHouseHouseId(id);
        houseResult.get().setImagesList(StreamSupport.stream(images.spliterator(), false).collect(Collectors.toList()));
        return houseResult.map(house -> new ResponseEntity<>(house, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<Iterable<Images>> getAllImageByProduct(@PathVariable Long id) {
        Optional<House> houseOptional = houseService.findById(id);
        return houseOptional.map(house -> new ResponseEntity<>(imageService.findAllByHouse(house), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public void setBookingStatusByCurrentDate(Date date) {
        List<Booking> listBookings = bookingService.setBookingStatusByCurrentDate(date);
        if (listBookings.isEmpty()) {
            return;
        }
        for (Booking booking : listBookings) {
            System.out.println(booking.getBookingStatus());
            booking.setBookingStatus(1);
            bookingService.save(booking);
            System.out.println("===================AFTER SET================");
            System.out.println(booking.getBookingStatus());

        }
    }

    public void setHouseStatusByCurrentDateAndBooking(List<House> houseList, Date current) {
        for (House house : houseList) {
            Booking booking = bookingService.findBookingHouseIdAndCurrentDate(house.getHouseId(), current);
            if (booking == null) {
                System.out.println(" Khong co Booking nao trong today thi houseStatus ->blank");
                if (house.getHouseStatus() != "upgrade" && house.getHouseStatus() != "blank") {
                    updateHouse(house.getHouseId(), "blank");
                }
            } else {
                System.out.println("Co nguoi dang thue houseStatus => rent");
                updateHouse(house.getHouseId(), "rent");
            }
            houseService.save(house);
        }

    }

    @GetMapping("/reviews/{houseId}")
    public ResponseEntity<Iterable<Review>> getReviewsOfHouse(@PathVariable Long houseId){
        Iterable<Review> reviews = reviewService.findAllByHouseHouseId(houseId);
        if(reviews == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
    @PostMapping("/createReview")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return new ResponseEntity<>(reviewService.save(review), HttpStatus.CREATED);
    }

}
