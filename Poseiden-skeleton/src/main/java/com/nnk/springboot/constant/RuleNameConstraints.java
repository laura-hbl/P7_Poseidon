package com.nnk.springboot.constant;

/**
 * Contains the different RuleName validator constraints.
 *
 * @author Laura Habdul
 */
public class RuleNameConstraints {

    /**
     * Empty constructor of class RuleNameConstraints.
     */
    public RuleNameConstraints() {
    }

    /**
     * Maximum number of characters required for name.
     */
    public static final int NAME_MAX_SIZE = 125;

    /**
     * Maximum number of characters required for description.
     */
    public static final int DESCRIPTION_MAX_SIZE = 125;

    /**
     * Maximum number of characters required for json.
     */
    public static final int JSON_MAX_SIZE = 125;

    /**
     * Maximum number of characters required for template.
     */
    public static final int TEMPLATE_MAX_SIZE = 512;

    /**
     * Maximum number of characters required for sql str.
     */
    public static final int SQL_STR_MAX_SIZE = 125;

    /**
     * Maximum number of characters required for sql part.
     */
    public static final int SQL_PART_MAX_SIZE = 125;
}
