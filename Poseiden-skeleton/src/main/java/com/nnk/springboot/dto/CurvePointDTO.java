package com.nnk.springboot.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class CurvePointDTO {

    private Integer id;

    private Integer curveId;

    private BigDecimal term;

    private BigDecimal value;
}
