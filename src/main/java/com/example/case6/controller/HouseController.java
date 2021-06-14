package com.example.case6.controller;

import com.example.case6.model.House;

import com.example.case6.model.Images;
import com.example.case6.service.house.IHouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@CrossOrigin("*")
@RequestMapping("houses")
public class HouseController {
    @Autowired
    private IHouseService houseService;

    @GetMapping
    public ResponseEntity<Iterable<House>> findAllHouse(){
        return new ResponseEntity<>(houseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable Long id) {
        Optional<House> houseOptional = houseService.findById(id);
        return houseOptional.map(house -> new ResponseEntity<>(house, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//    @GetMapping
//    public ResponseEntity<Map<Long,List<Images>>> listImagesWithHouseId(){
//        Map<Long,List<Images>> listImagesWithHouseId = new HashMap<>();
//        for (House house: houseService.findAll()) {
//            listImagesWithHouseId.put(house.getHouseId(),house.getImagesList());
//        }
//        return  new ResponseEntity<>(listImagesWithHouseId,HttpStatus.OK);
//    }



}
