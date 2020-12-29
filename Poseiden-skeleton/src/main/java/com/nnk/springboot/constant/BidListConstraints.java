package com.nnk.springboot.constant;

/**
 * Contains the different BidList validator constraints.
 *
 * @author Laura Habdul
 */
public class BidListConstraints {

    /**
     * Empty constructor of class BidListConstraints.
     */
    public BidListConstraints() {
    }

    /**
     * Maximum number of characters required for account.
     */
    public static final int ACCOUNT_MAX_SIZE = 30;

    /**
     * Maximum number of characters required for type.
     */
    public static final int TYPE_MAX_SIZE = 30;

    /**
     * Minimum value authorized for bid quantity.
     */
    public static final int BID_QUANTITY_MIN_VALUE = 0;
}
