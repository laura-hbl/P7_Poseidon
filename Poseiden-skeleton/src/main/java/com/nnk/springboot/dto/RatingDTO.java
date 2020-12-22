package com.nnk.springboot.dto;

import com.nnk.springboot.constant.RatingConstraints;
import javax.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class RatingDTO {

    private Integer id;

    @Length(max = RatingConstraints.MOODY_RATING_MAX_SIZE, message = "The maximum length for account is 125 characters")
    private String moodysRating;

    @Length(max = RatingConstraints.STAND_P_RATING_MAX_SIZE, message = "The maximum length for account is 125 characters")
    private String standPoorRating;

    @Length(max = RatingConstraints.FITCH_RATING_MAX_SIZE, message = "The maximum length for account is 125 characters")
    private String fitchRating;

    @Min(value = 0, message = "The value must be positive")
    private Integer orderNumber;

    public RatingDTO(final String moodysRating, final String standPoorRating, final String fitchRating,
                     final Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.standPoorRating = standPoorRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
