package com.example.case6.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseId;

    @NotNull
    private String houseName;

    @NotNull
    private String houseAddress;
    @NotNull
    private String area;
    @Column(nullable = false)
    private String type;
    @NotNull
    private String bedroomQuantity;
    @NotNull
    private String bathroomQuantity;
    @NotNull
    private String description;
    @NotNull
    private String pricePerDay;
    @NotNull
    private String houseStatus;

    @OneToMany
    @Transient

    private List<Images> imagesList;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

//    //duoc
//    private Long rate;
}
