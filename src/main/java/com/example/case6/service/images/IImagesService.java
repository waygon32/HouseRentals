package com.example.case6.service.images;

import com.example.case6.model.House;
import com.example.case6.model.Images;

public interface IImagesService {
    Iterable<Images> getImagesByHouseId(Long houseId);
}
