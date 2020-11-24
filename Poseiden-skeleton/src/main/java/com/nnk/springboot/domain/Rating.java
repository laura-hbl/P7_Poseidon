package com.nnk.springboot.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "rating")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "moodys_rating")
    private String moodysRating;

    @Column(name = "stand_poor_rating")
    private String standPoorRating;

    @Column(name = "fitch_rating")
    private String fitchRating;

    @Column(name = "order_number")
    private Integer orderNumber;

}
