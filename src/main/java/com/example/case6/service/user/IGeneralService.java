package com.example.case6.service.user;

import java.util.Optional;

public interface IGeneralService<T> {
    Optional<T> fillbyId(Long id);
    T save(T t);
}
