package com.nnk.springboot.dto;

import com.nnk.springboot.constant.RuleNameConstraints;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class RuleNameDTO {

    private Integer id;

    @Length(max = RuleNameConstraints.NAME_MAX_SIZE, message = "The maximum length for name is 125 characters")
    private String name;

    @Length(max = RuleNameConstraints.DESCRIPTION_MAX_SIZE, message = "The maximum length for description is 125 characters")
    private String description;

    @Length(max = RuleNameConstraints.JSON_MAX_SIZE, message = "The maximum length for json is 125 characters")
    private String json;

    @Length(max = RuleNameConstraints.TEMPLATE_MAX_SIZE, message = "The maximum length for template is 512 characters")
    private String template;

    @Length(max = RuleNameConstraints.SQL_STR_MAX_SIZE, message = "The maximum length for sqlStr is 125 characters")
    private String sqlStr;

    @Length(max = RuleNameConstraints.SQL_PART_MAX_SIZE, message = "The maximum length for sqlPart is 125 characters")
    private String sqlPart;

    public RuleNameDTO (final String name, final String description, final String json, final String template,
                        final String sqlStr, final String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
