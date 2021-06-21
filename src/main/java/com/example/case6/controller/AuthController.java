package com.example.case6.controller;

import com.example.case6.message.ResponseMessage;
import com.example.case6.model.*;
import com.example.case6.service.role.RoleService;
import com.example.case6.service.user.IUserService;
import com.example.case6.service.user.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class AuthController {
    public static final String CONFIRM_SUCCESS = "Confirm Success";
    public static final String CONFIRM_FAIL = "confirm fail";
    public static final String LOGINSUCCESS = "Success";
    public static final String LOGINFALSE = "Login false";
    public static final String FALSE = "False";
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
    public ResponseEntity<?> login(@RequestBody Users users) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Users curentUser = userService.findByUsername(users.getUsername());
            return ResponseEntity.ok(new JwtResponse(curentUser.getUserId(), userDetails.getUsername(), jwt, userDetails.getAuthorities()));
        } catch (DisabledException e) {
            e.printStackTrace();
            return new ResponseEntity<>(LOGINFALSE, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(LOGINFALSE, HttpStatus.OK);
        }
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
    public ResponseEntity<Users> updateUser(@RequestBody Users users) {
        Users currentUser = userService.findbyId(getCurrentUser().getId());
        if (currentUser.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(users.getEmail()) &&
                !(users.getEmail().equals(currentUser.getEmail())) ||
                userService.existByPhoneNumber(users.getPhone()) &&
                        !(users.getPhone().equals(currentUser.getPhone()))
        ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        currentUser.setFullname(users.getFullname());
        currentUser.setUserAddress(users.getUserAddress());
        currentUser.setPhone(users.getPhone());
        currentUser.setEmail(users.getEmail());
        currentUser.setAvatarUrl(users.getAvatarUrl());
//            currentUser.setRoles(users.getRoles());
        userService.save(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);

    }

//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            new SecurityContextLogoutHandler().logout(request, response, authentication);
//        }
//        return  new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("/confirmPassword/{password}")
    public ResponseEntity<Boolean> confirmPassword(@PathVariable("password") String password) {
        Users userCurrent = userService.findbyId(getCurrentUser().getId());
//        Users userCurrent = userService.findByUsername(getPrincipal());
     return ResponseEntity.ok(passwordEncoder.matches(password, userCurrent.getPassword()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUser(@PathVariable("id") Long id) {
        Users users = userService.findbyId(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("checkusername/{username}")
    public ResponseEntity<Boolean> isUsernameExists(@PathVariable String username) {
        return ResponseEntity.ok(userService.existsByUsername(username));
    }

    @GetMapping("checkemail/{email}")
    public ResponseEntity<Boolean> isEmailExists(@PathVariable String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

    @GetMapping("checkphone/{phone}")
    public ResponseEntity<Boolean> isPhoneNumberExists(@PathVariable String phone) {
        return ResponseEntity.ok(userService.existByPhoneNumber(phone));
    }


    // duoc-----------------------------------------------------------------------

    @RequestMapping(value = "/user/confirmPassword/{password}", method = RequestMethod.GET)
    public ResponseEntity<?> comparePassword(@PathVariable("password") String password) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(getCurrentUser().getUsername(), password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserPrinciple user = (UserPrinciple) userDetails;
            Users curentUser = userService.findByUsername(user.getUsername());
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(false, CONFIRM_FAIL, null), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/user/current")
    public ResponseEntity<?> getUserById() {
        long userId = getCurrentUser().getId();
        Users user = userService.findbyId(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/updateCurrent/{password}/{newPassword}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser( @PathVariable("password") String password,@PathVariable("newPassword") String newPassword) {
        Users userCurrent = userService.findbyId(getCurrentUser().getId());
        if(passwordEncoder.matches(password, userCurrent.getPassword())) {
        Users currentUser = userService.findbyId(getCurrentUser().getId());
       userCurrent.setPassword(passwordEncoder.encode(newPassword));
        userService.save(userCurrent);

        return new ResponseEntity<Users>(userCurrent, HttpStatus.OK);
        }
        return new ResponseEntity(FALSE, HttpStatus.OK);
    }

//*******************************************************************************************************
}
