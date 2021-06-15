package com.example.case6.repository.users;

import com.example.case6.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);
    Users findUsersByUserId(Long id);
}
