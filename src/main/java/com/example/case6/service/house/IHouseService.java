package com.example.case6.service.house;

import com.example.case6.model.House;
import com.example.case6.service.IGeneralService;

public interface IHouseService extends IGeneralService<House> {
    Iterable<House> findAll();

}
