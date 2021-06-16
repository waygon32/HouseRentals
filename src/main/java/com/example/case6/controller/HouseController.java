package com.example.case6.controller;

import com.example.case6.model.House;

import com.example.case6.model.Images;
import com.example.case6.repository.IImageRepository;
import com.example.case6.service.house.IHouseService;
import com.example.case6.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin("*")
@RequestMapping("/houses")
public class HouseController {
    @Autowired
    private IHouseService houseService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IImageRepository imageRepository;

    @GetMapping
    public ResponseEntity<Iterable<House>> findAllHouse() {
        return new ResponseEntity<>(houseService.findAll(), HttpStatus.OK);
    }
//    @GetMapping("/pagination")
//    public ResponseEntity<Iterable<House>> getAllHouseUsingPagination(@RequestParam int page, @RequestParam int size) {
//        Iterable<House> houses = houseService.findAllHouse(page, size);
//        return new ResponseEntity<>(houses, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<House> createHouse(@RequestBody House house) {
        return new ResponseEntity<>(houseService.save(house), HttpStatus.OK);
    }

//    @GetMapping("/detail/{id}")
//    public ResponseEntity<House> getHouseById(@PathVariable Long id) {
//        Optional<House> houseOptional = houseService.findById(id);
//        Iterable<Images> images = imageRepository.getImagesByHouseHouseId(id);
//        houseOptional.get().setImagesList(StreamSupport.stream(images.spliterator(), false).collect(Collectors.toList()));
//
//        return houseOptional.map(house -> new ResponseEntity<>(house, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

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
    @GetMapping("/detail/{id}")
    public ResponseEntity<House> getProductDetail(@PathVariable Long id) {
        Optional<House> houseResult = houseService.findById(id);
        Iterable<Images> images = imageRepository.findImagesByHouseHouseId(id);
        houseResult.get().setImagesList(StreamSupport.stream(images.spliterator(), false).collect(Collectors.toList()));
        return houseResult.map(house -> new ResponseEntity<>(house,HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//        return houseOptional.map(house -> new ResponseEntity<>(imageService.findAllByHouse(house), HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
