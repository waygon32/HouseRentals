package com.example.case6.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;



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
