package com.example.case6.controller;

import com.example.case6.model.JwtResponse;
import com.example.case6.model.Role;
import com.example.case6.model.RoleName;
import com.example.case6.model.Users;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
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
    public  ResponseEntity<Users> register( @RequestBody Users users){
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
//    @PostMapping("/editpassword")
}
