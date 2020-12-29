package com.nnk.springboot.dto;

import com.nnk.springboot.constant.RuleNameConstraints;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Permits the storage and retrieving data of a ruleName.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class RuleNameDTO {

    /**
     * Id of the ruleName.
     */
    private Integer id;

    /**
     * Name of the ruleName.
     */
    @Length(max = RuleNameConstraints.NAME_MAX_SIZE, message = "The maximum length for name is 125 characters")
    private String name;

    /**
     * Description of the ruleName.
     */
    @Length(max = RuleNameConstraints.DESCRIPTION_MAX_SIZE, message = "The maximum length for description is 125 characters")
    private String description;

    /**
     * Json of the ruleName.
     */
    @Length(max = RuleNameConstraints.JSON_MAX_SIZE, message = "The maximum length for json is 125 characters")
    private String json;

    /**
     * Template of the ruleName.
     */
    @Length(max = RuleNameConstraints.TEMPLATE_MAX_SIZE, message = "The maximum length for template is 512 characters")
    private String template;

    /**
     * SQL str of the ruleName.
     */
    @Length(max = RuleNameConstraints.SQL_STR_MAX_SIZE, message = "The maximum length for sqlStr is 125 characters")
    private String sqlStr;

    /**
     * SQL part of the ruleName.
     */
    @Length(max = RuleNameConstraints.SQL_PART_MAX_SIZE, message = "The maximum length for sqlPart is 125 characters")
    private String sqlPart;

    /**
     * Constructor of class RuleNameDTO.
     * Initialize name, description, json, template, sqlStr and sqlPart.
     *
     * @param name        name of the ruleName
     * @param description description of the ruleName
     * @param json        json of the ruleName
     * @param template    template of the ruleName
     * @param sqlStr      sqlStr of the ruleName
     * @param sqlPart     sqlPart of the ruleName
     */
    public RuleNameDTO(final String name, final String description, final String json, final String template,
                       final String sqlStr, final String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
