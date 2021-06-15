package com.example.case6.service.user;

import com.example.case6.model.Users;
import com.example.case6.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<Users>, UserDetailsService {
    Users findByUsername(String name);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existByPhoneNumber(String phone);
}
