package com.example.case6.service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T>  {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
}
