package com.example.case6.controller;

import com.example.case6.model.House;

import com.example.case6.model.Images;
import com.example.case6.model.Users;
import com.example.case6.repository.IImageRepository;
import com.example.case6.service.house.IHouseService;
import com.example.case6.service.image.IImageService;
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
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin("*")
@RequestMapping("/houses")
public class HouseController {
    //    convert value string to date
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(       Date.class,
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

    @GetMapping
    public ResponseEntity<Iterable<House>> findAllHouse() {
        return new ResponseEntity<>(houseService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/pagination")
    public ResponseEntity<Iterable<House>> getAllHouseUsingPagination(@RequestParam int page, @RequestParam int size) {
        Iterable<House> houses = houseService.findAllHouse(page, size);
        return new ResponseEntity<>(houses, HttpStatus.OK);
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
    @GetMapping("/{id}/images")
    public ResponseEntity<Iterable<Images>> getAllImageByProduct(@PathVariable Long id) {
        Optional<House> houseOptional = houseService.findById(id);
        return houseOptional.map(house -> new ResponseEntity<>(imageService.findAllByHouse(house), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<House> getProductDetail(@PathVariable Long id) {
        Optional<House> houseResult = houseService.findById(id);
        Iterable<Images> images = imageRepository.findImagesByHouseHouseId(id);
        houseResult.get().setImagesList(StreamSupport.stream(images.spliterator(), false).collect(Collectors.toList()));
        return houseResult.map(house -> new ResponseEntity<>(house, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
