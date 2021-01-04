package com.nnk.springboot.dto;

import com.nnk.springboot.constant.RatingConstraints;
import javax.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Permits the storage and retrieving data of a rating.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class RatingDTO {

    /**
     * Id of the rating.
     */
    private Integer id;

    /**
     * Moody's rating.
     */
    @Length(max = RatingConstraints.MOODY_RATING_MAX_SIZE, message = "The maximum length for account is 125 characters")
    private String moodysRating;

    /**
     * Standard and Poor's rating.
     */
    @Length(max = RatingConstraints.STAND_P_RATING_MAX_SIZE, message = "The maximum length for account is 125 characters")
    private String standPoorRating;

    /**
     * Fitch's rating.
     */
    @Length(max = RatingConstraints.FITCH_RATING_MAX_SIZE, message = "The maximum length for account is 125 characters")
    private String fitchRating;

    /**
     * Order number of the rating.
     */
    @Min(value = RatingConstraints.ORDER_NUMBER_MIN_VALUE, message = "The value must be positive")
    private Integer orderNumber;

    /**
     * Constructor of class RatingDTO.
     * Initialize moodysRating, standPoorRating, fitchRating and orderNumber.
     *
     * @param moodysRating    rating of moody
     * @param standPoorRating rating of standPoor
     * @param fitchRating     Fitch's rating.
     * @param orderNumber     order number of the rating
     */
    public RatingDTO(final String moodysRating, final String standPoorRating, final String fitchRating,
                     final Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.standPoorRating = standPoorRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
