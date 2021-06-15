package com.example.case6.service.image;

import com.example.case6.model.House;
import com.example.case6.model.Images;
import com.example.case6.service.IGeneralService;

public interface IImageService extends IGeneralService<Images> {
//    Iterable<Images> getImagesByHouseId(Long houseId);

    Iterable<Images> findAllByHouse(House house);
}
