package com.nnk.springboot.constant;

/**
 * Contains the different Trade validator constraints.
 *
 * @author Laura Habdul
 */
public class TradeConstraints {

    /**
     * Empty constructor of class TradeConstraints.
     */
    public TradeConstraints() {
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
     * Minimum value authorized for buy quantity.
     */
    public static final int BUY_QUANTITY_MIN_VALUE = 0;
}
