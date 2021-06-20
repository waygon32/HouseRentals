package com.example.case6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @Nullable
    private String comment;
    @NotNull
    private int rating;
    private Date postDate;
    @ManyToOne
    @JoinColumn
    private Users user;
    @ManyToOne
    private House house;


//    @OneToOne
//    @JoinColumn(name = "booking_id")
//    private Booking booking;

}
