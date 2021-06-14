package com.example.case6.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Date checkinDate;

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
