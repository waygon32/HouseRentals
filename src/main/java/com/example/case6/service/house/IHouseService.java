package com.example.case6.service.house;

import com.example.case6.model.House;
import com.example.case6.service.IGeneralService;

import java.util.Date;

public interface IHouseService extends IGeneralService<House> {
    Iterable<House> findHouse(String search, Date checkin, Date checkout);

//    Iterable<House> findAllHouse(int page, int size);
  Iterable<House> getListHouseOfUser(Long id );
}