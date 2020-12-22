package com.nnk.springboot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class CurvePointDTO {

    private Integer id;

    @NotNull(message = "must not be null")
    private Integer curveId;

    @Min(value = 0, message = "The value must be positive")
    private BigDecimal term;

    @Min(value = 0, message = "The value must be positive")
    private BigDecimal value;

    public CurvePointDTO(final Integer curveId, final BigDecimal term, final BigDecimal value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
