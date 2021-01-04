package com.nnk.springboot.domain;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Permits the storage and retrieving data of a curvePoint.
 *
 * @author Laura Habdul
 */
@Entity
@Table(name = "curve_point")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class CurvePoint {

    /**
     * Id of the curvePoint.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * Id of curve.
     */
    @Column(name = "curve_id")
    private Integer curveId;

    /**
     * As of Date of the curvePoint.
     */
    @Column(name = "as_of_date")
    private LocalDateTime asOfDate;

    /**
     * Term of the curvePoint.
     */
    private BigDecimal term;

    /**
     * Value of the curvePoint.
     */
    private BigDecimal value;

    /**
     * Date and time of the curvePoint creation.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Constructor of class CurvePoint.
     * Initialize curveId, term and value.
     *
     * @param curveId id of curve
     * @param term    term of the curvePoint
     * @param value   value of the curvePoint
     */
    public CurvePoint(final Integer curveId, final BigDecimal term, final BigDecimal value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
