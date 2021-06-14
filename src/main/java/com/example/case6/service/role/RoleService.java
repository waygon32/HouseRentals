package com.example.case6.service.role;

import com.example.case6.model.Role;
import com.example.case6.model.RoleName;
import com.example.case6.repository.users.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    IRoleRepository  roleRepository;
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}