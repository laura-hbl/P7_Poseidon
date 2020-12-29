package com.nnk.springboot.dto;

import com.nnk.springboot.constant.CurvePointConstraints;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * Permits the storage and retrieving data of a curvePoint.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class CurvePointDTO {

    /**
     * Id of the curvePoint.
     */
    private Integer id;

    /**
     * Id of curve.
     */
    @NotNull(message = "must not be null")
    private Integer curveId;

    /**
     * Term of the curvePoint.
     */
    @Min(value = CurvePointConstraints.TERM_MIN_VALUE, message = "The value must be positive")
    private BigDecimal term;

    /**
     * Value of the curvePoint.
     */
    @Min(value = CurvePointConstraints.VALUE_MIN_VALUE, message = "The value must be positive")
    private BigDecimal value;

    /**
     * Constructor of class CurvePointDTO.
     * Initialize curveId, term and value.
     *
     * @param curveId id of curve
     * @param term    term of the curvePoint
     * @param value   value of the curvePoint
     */
    public CurvePointDTO(final Integer curveId, final BigDecimal term, final BigDecimal value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
