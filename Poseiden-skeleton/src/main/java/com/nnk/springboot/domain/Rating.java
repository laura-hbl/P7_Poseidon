package com.nnk.springboot.domain;

import javax.persistence.*;
import lombok.*;

/**
 * Permits the storage and retrieving data of a rating.
 *
 * @author Laura Habdul
 */
@Entity
@Table(name = "rating")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class Rating {

    /**
     * Id of the rating.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * Rating of Moody.
     */
    @Column(name = "moodys_rating")
    private String moodysRating;

    /**
     * Rating of Stand Poor.
     */
    @Column(name = "stand_poor_rating")
    private String standPoorRating;

    /**
     * Rating of Fitch.
     */
    @Column(name = "fitch_rating")
    private String fitchRating;

    /**
     * Order number of the rating.
     */
    @Column(name = "order_number")
    private Integer orderNumber;
}
