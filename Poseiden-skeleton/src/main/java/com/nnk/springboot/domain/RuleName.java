package com.nnk.springboot.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "rule_name")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    private String name;

    private String description;

    private String json;

    private String template;

    @Column(name = "sql_str")
    private String sqlStr;

    @Column(name = "sql_part")
    private String sqlPart;

}
