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
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class AuthController {
    public static final String CONFIRM_SUCCESS = "Confirm Success";
    public static final String CONFIRM_FAIL = "confirm fail";
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
    public ResponseEntity<?> login(@RequestBody Users users) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users curentUser = userService.findByUsername(users.getUsername());
        return ResponseEntity.ok(new JwtResponse(curentUser.getUserId(), userDetails.getUsername(), jwt, userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users users, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUsername(users.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(users.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.existByPhoneNumber(users.getPhone())) {
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

    @PutMapping("/edit-profile")
    public ResponseEntity<Users> updateUser(@RequestBody Users users, BindingResult bindingResult) {
        Users currentUser = userService.findbyId(getCurrentUser().getId());
        if (currentUser.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        currentUser.setFullname(users.getFullname());
        currentUser.setUserAddress(users.getUserAddress());
        currentUser.setPhone(users.getPhone());
        currentUser.setEmail(users.getEmail());
        currentUser.setAvatarUrl(users.getAvatarUrl());
        currentUser.setRoles(users.getRoles());
        userService.update(currentUser);
        return new ResponseEntity<Users>(currentUser, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @PostMapping("/confirmPassword")
    public ResponseEntity<?> confirmPassword(@RequestBody String password) {
        Users currentUser = userService.findbyId(getCurrentUser().getId());
        if (currentUser.getPassword().equals(passwordEncoder.encode(password))) {
            return new ResponseEntity<>(CONFIRM_SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(CONFIRM_FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUser(@PathVariable("id") Long id) {
        Users users = userService.findbyId(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
