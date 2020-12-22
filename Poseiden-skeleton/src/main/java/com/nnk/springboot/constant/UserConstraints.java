package com.nnk.springboot.constant;

/**
 * Contains the different User validator constraints.
 *
 * @author Laura Habdul
 */
public class UserConstraints {

    /**
     * Empty constructor of class UserConstraints.
     */
    public UserConstraints() {
    }

    /**
     * Maximum number of characters allowed for the full name.
     */
    public static final int FULL_NAME_MAX_SIZE = 125;

    /**
     * Maximum number of characters allowed for the username.
     */
    public static final int USERNAME_MAX_SIZE = 125;

    /**
     * Maximum number of characters required for password.
     */
    public static final int PASSWORD_MAX_SIZE = 125;

    /**
     * User password pattern.
     */
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*-])(?=\\S+$).{8,}$";
}
