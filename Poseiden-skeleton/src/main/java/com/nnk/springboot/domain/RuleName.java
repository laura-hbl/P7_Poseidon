package com.nnk.springboot.domain;

import javax.persistence.*;
import lombok.*;

/**
 * Permits the storage and retrieving data of a ruleName.
 *
 * @author Laura Habdul
 */
@Entity
@Table(name = "rule_name")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class RuleName {

    /**
     * Id of the ruleName.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * Name of the ruleName.
     */
    private String name;

    /**
     * Description of the ruleName.
     */
    private String description;

    /**
     * Json of the ruleName.
     */
    private String json;

    /**
     * Template of the ruleName.
     */
    private String template;

    /**
     * SQL str of the ruleName.
     */
    @Column(name = "sql_str")
    private String sqlStr;

    /**
     * SQL part of the ruleName.
     */
    @Column(name = "sql_part")
    private String sqlPart;

    /**
     * Constructor of class RuleName.
     * Initialize name, description, json, template, sqlStr and sqlPart.
     *
     * @param name        name of the ruleName
     * @param description description of the ruleName
     * @param json        json of the ruleName
     * @param template    template of the ruleName
     * @param sqlStr      SQL str of the ruleName
     * @param sqlPart     SQL part of the ruleName
     */
    public RuleName(final String name, final String description, final String json, final String template,
                    final String sqlStr, final String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
