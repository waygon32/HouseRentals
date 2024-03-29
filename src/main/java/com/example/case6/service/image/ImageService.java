package com.example.case6.service.image;

import com.example.case6.model.House;
import com.example.case6.model.Images;
import com.example.case6.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService implements IImageService {
    @Autowired
    private IImageRepository imageRepository;

    @Override
    public Iterable<Images> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Images> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Images save(Images images) {
        return imageRepository.save(images);
    }

    @Override
    public void remove(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public void create(Images model) {

    }

    @Override
    public Iterable<Images> getImagesByHouseId(Long houseId) {
        return imageRepository.getImagesByHouseHouseId(houseId);
    }

    @Override
    public Iterable<Images> findAllByHouse(House house) {
        return imageRepository.findAllByHouse(house);
    }

    @Override
    public Iterable<Images> findImagesByHouseHouseId(Long id) {
        return imageRepository.findImagesByHouseHouseId(id);
    }
}
