package com.example.case6.controller;

import com.example.case6.model.House;

import com.example.case6.service.house.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/houses")
public class HouseController {
    @Autowired
    private IHouseService houseService;

    @GetMapping
    public ResponseEntity<Iterable<House>> findAllHouse() {
        return new ResponseEntity<>(houseService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<House> createHouse(@RequestBody House house) {
        return new ResponseEntity<>(houseService.save(house), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
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
        return new ResponseEntity<>(houseService.findHouse(search, checkin, checkout), HttpStatus.OK);
    }
}
