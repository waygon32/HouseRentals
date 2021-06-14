package com.example.case6.service;


import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    Iterable<T> findAll(int page , int size);    Optional<T> findById(Long id);

    T save(T t);

    void remove(Long id);



}
