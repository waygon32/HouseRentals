package com.example.case6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Images{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY )
    private Long id ;

    @ManyToOne(cascade=CascadeType.MERGE,fetch =FetchType.LAZY)
    private House house;
    @Nullable
    private String linkImage;
//    @NotNull
//    private Long houseId;
}
