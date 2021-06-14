package com.example.case6.service;

import java.util.List;

public interface IGeneralService <T>{
    Iterable<T> findAll(int page , int size);
    void deleteById(Long id);
    T save(T data);
    T findById(Long id);
    List<T> getAllBookingByHouseId(Long id);

}
