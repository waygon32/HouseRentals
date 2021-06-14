package com.example.case6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long bookingId;
    @FutureOrPresent
    private Date checkinDate;
    @FutureOrPresent
    private Date checkoutDate;
    private String total;
    private int bookingStatus;
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

}
