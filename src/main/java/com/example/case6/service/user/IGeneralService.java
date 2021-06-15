package com.example.case6.service.user;

import java.util.Optional;

public interface IGeneralService<T> {
    T findbyId(Long id);
    T save(T t);
}
