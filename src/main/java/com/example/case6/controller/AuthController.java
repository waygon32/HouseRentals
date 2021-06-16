package com.example.case6.controller;

import com.example.case6.model.*;
import com.example.case6.service.role.RoleService;
import com.example.case6.service.user.IUserService;
import com.example.case6.service.user.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users curentUser = userService.findByUsername(users.getUsername());
        return ResponseEntity.ok(new JwtResponse(curentUser.getUserId(), userDetails.getUsername(), jwt ,userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public  ResponseEntity<Users> register( @RequestBody Users users, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUsername(users.getUsername())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(users.getEmail())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userService.existByPhoneNumber(users.getPhone())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
        users.setRoles(roles);
        return new ResponseEntity<>(userService.save(users), HttpStatus.CREATED);
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<Users> updateUser(@Valid @RequestBody Users users, BindingResult bindingResult) {
        Users currentUser = userService.findbyId(getCurrentUser().getId());
        currentUser.setFullname(users.getFullname());
        currentUser.setUserAddress(users.getUserAddress());
        currentUser.setPhone(users.getPhone());
        currentUser.setEmail(users.getEmail());
        currentUser.setAvatarUrl(users.getAvatarUrl());
        userService.update(currentUser);
        return new ResponseEntity<Users>(currentUser, HttpStatus.OK);
    }
}
