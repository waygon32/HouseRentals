package com.example.case6.service;


import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    T save(T t);
    void remove(Long id);

    //duoc
    void create(T model);
    //--------------------------
}
