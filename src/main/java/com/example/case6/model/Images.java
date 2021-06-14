package com.example.case6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Images{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id ;

    @Nullable
    private String linkImage;

    @ManyToOne(cascade=CascadeType.MERGE)
    private House house;


}
