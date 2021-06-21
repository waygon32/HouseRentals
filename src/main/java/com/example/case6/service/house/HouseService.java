package com.example.case6.service.house;

import com.example.case6.model.House;
import com.example.case6.model.Images;
import com.example.case6.repository.IHouseRepository;
import com.example.case6.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HouseService implements IHouseService {
    @Autowired
    private IHouseRepository houseRepository;
    @Autowired
    private IImageRepository imageRepository;
//    @Override
//    public Iterable<House> findAll() {
//        return houseRepository.findAll();
//    }

    @Override
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Override
    public void remove(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public void create(House model) {

    }

    @Override
    public Iterable<House> findHouse(String search, Date checkin, Date checkout) {
        return houseRepository.findHouse(search,checkin,checkout);
    }



    @Override
    public Iterable<House> findAll() {
        Iterable<House> houses = houseRepository.findAll();
        houses.forEach(house -> {
            Iterable<Images> images = imageRepository.findImagesByHouseHouseId(house.getHouseId());
            //Đổi iterable -> list
            house.setImagesList(StreamSupport.stream(images.spliterator(), false).collect(Collectors.toList()));
        });

        return houses;
    }

    @Override
    public Optional<House> findById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public Iterable<House> getListHouseOfUser(Long id) {
        return houseRepository.findHousesByUsersUserId(id);
    }

}
