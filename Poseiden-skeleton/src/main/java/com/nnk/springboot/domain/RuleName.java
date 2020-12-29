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
}
