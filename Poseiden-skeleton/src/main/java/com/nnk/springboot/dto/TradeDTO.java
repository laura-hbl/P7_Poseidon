package com.nnk.springboot.dto;

import com.nnk.springboot.constant.TradeConstraints;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * Permits the storage and retrieving data of a trade.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class TradeDTO {

    /**
     * Id of the trade.
     */
    private Integer id;

    /**
     * Account of the trade.
     */
    @NotEmpty(message = "Account is mandatory")
    @Length(max = TradeConstraints.ACCOUNT_MAX_SIZE, message = "The maximum length for account is 30 characters")
    private String account;

    /**
     * Type of the trade.
     */
    @NotEmpty(message = "Type is mandatory")
    @Length(max = TradeConstraints.TYPE_MAX_SIZE, message = "The maximum length for type is 30 characters")
    private String type;

    /**
     * Buy quantity of the trade.
     */
    @Min(value = TradeConstraints.BUY_QUANTITY_MIN_VALUE, message = "The value must be positive")
    private BigDecimal buyQuantity;

    /**
     * Constructor of class TradeDTO.
     * Initialize account, type and buyQuantity.
     *
     * @param account     account of the trade
     * @param type        type of the trade
     * @param buyQuantity buyQuantity of the trade
     */
    public TradeDTO(final String account, final String type, final BigDecimal buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}
