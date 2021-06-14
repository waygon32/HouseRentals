package com.example.case6.service.images;

import com.example.case6.model.House;
import com.example.case6.model.Images;
import com.example.case6.repository.IImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class ImagesService implements IImagesService {
    @Autowired
    private IImagesRepository imagesRepository;
    @Override
    public Iterable<Images> getImagesByHouseId(Long houseId) {
        return imagesRepository.getImagesByHouseId(houseId);
    }
}
