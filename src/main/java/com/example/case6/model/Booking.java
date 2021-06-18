package com.example.case6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.util.Date;
import javax.validation.constraints.NotNull;




@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date checkinDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date checkoutDate;
    @NotNull
    private String total;
    @Column(columnDefinition = "varchar(50) default '-1'", insertable = false)
    private Integer bookingStatus;
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

}
