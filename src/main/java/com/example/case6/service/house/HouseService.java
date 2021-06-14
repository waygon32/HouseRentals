package com.example.case6.service.house;

import com.example.case6.model.House;
import com.example.case6.repository.IHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
public class HouseService implements IHouseService {
    @Autowired
    private IHouseRepository houseRepository;
    @Override
    public Iterable<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public Optional<House> findById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Override
    public void remove(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public Iterable<House> findHouse(String search, Date checkin, Date checkout) {
        return houseRepository.findHouse(search,checkin,checkout);
    }
}
