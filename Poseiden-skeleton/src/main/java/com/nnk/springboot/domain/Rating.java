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
     * Moody's rating.
     */
    @Column(name = "moodys_rating")
    private String moodysRating;

    /**
     * Standard and Poor's rating.
     */
    @Column(name = "stand_poor_rating")
    private String standPoorRating;

    /**
     * Fitch's rating.
     */
    @Column(name = "fitch_rating")
    private String fitchRating;

    /**
     * Order number of the rating.
     */
    @Column(name = "order_number")
    private Integer orderNumber;

    /**
     * Constructor of class Rating.
     * Initialize moodysRating, standPoorRating and orderNumber.
     *
     * @param moodysRating    Moody's rating
     * @param standPoorRating Standard and Poor's rating
     * @param fitchRating     Fitch's rating
     * @param orderNumber     order number of the rating
     */
    public Rating(final String moodysRating, final String standPoorRating, final String fitchRating,
                  final Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.standPoorRating = standPoorRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
