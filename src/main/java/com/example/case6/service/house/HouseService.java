package com.example.case6.service.house;

import com.example.case6.model.House;
import com.example.case6.model.Images;
import com.example.case6.repository.IHouseRepository;
import com.example.case6.repository.IImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HouseService implements IHouseService {
    @Autowired
    private IHouseRepository houseRepository;
    @Autowired
    private IImagesRepository imageRepository;

    @Override
    public Iterable<House> findAll() {
        Iterable<House> houses = houseRepository.findAll();
        houses.forEach(house -> {
            Iterable<Images> images = imageRepository.getImagesByHouseId(house.getHouseId());
            //Đổi iterable -> list
            house.setImagesList(StreamSupport.stream(images.spliterator(), false).collect(Collectors.toList()));
        });

        return houses;
    }

    @Override
    public Optional<House> findById(Long id) {
        return houseRepository.findById(id);
    }
}
