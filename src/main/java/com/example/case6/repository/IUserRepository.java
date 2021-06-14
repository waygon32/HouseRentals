package com.example.case6.repository;

import com.example.case6.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IUserRepository extends JpaRepository<Users,Long> {
}
