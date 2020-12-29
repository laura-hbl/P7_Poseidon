package com.nnk.springboot.constant;

/**
 * Contains the different Rating validator constraints.
 *
 * @author Laura Habdul
 */
public class RatingConstraints {

    /**
     * Empty constructor of class RatingConstraints.
     */
    public RatingConstraints() {
    }

    /**
     * Maximum number of characters required for stand poor rating.
     */
    public static final int STAND_P_RATING_MAX_SIZE = 125;

    /**
     * Maximum number of characters required for fitch rating.
     */
    public static final int FITCH_RATING_MAX_SIZE = 125;

    /**
     * Maximum number of characters required for moody's rating.
     */
    public static final int MOODY_RATING_MAX_SIZE = 125;

    /**
     * Minimum value authorized for order number.
     */
    public static final int ORDER_NUMBER_MIN_VALUE = 0;
}
