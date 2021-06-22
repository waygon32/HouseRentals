package com.example.case6.model;

import com.example.case6.model.Booking;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.method.support.InvocableHandlerMethod;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.List;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Size(max = 30)
    @Pattern(regexp = "[A-Za-z0-9 ]+")
    private String fullname;

    @NotNull
    @Size(min = 6, max=20)
    @Pattern(regexp = "^(?=[a-zA-Z0-9._])(?!.*[_.]{2})[^_.].*[^_.]$")
    private String username;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "((0)+([0-9]{9})\\b)")
    private String phone;

    @NotNull
    private String userAddress;

    @Email
    @NotNull
    private String email;

    private String avatarUrl;

    @OneToMany
    @JsonIgnore
    private List<Booking> bookingList;
     @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
