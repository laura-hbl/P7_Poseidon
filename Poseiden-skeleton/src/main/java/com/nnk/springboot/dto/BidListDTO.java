package com.nnk.springboot.dto;

import com.nnk.springboot.constant.BidListConstraints;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * Permits the storage and retrieving data of a bidList.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class BidListDTO {

    /**
     * Id of the bidList.
     */
    private Integer id;

    /**
     * Account of the bidList.
     */
    @NotEmpty(message = "Account is mandatory")
    @Length(max = BidListConstraints.ACCOUNT_MAX_SIZE, message = "The maximum length for account is 30 characters")
    private String account;

    /**
     * Type of the BidList.
     */
    @NotEmpty(message = "Type is mandatory")
    @Length(max = BidListConstraints.TYPE_MAX_SIZE, message = "The maximum length for type is 30 characters")
    private String type;

    /**
     * Numbers of bid.
     */
    @Min(value = BidListConstraints.BID_QUANTITY_MIN_VALUE, message = "The value must be positive")
    private BigDecimal bidQuantity;

    /**
     * Constructor of class BidListDTO.
     * Initialize account, type and bidQuantity.
     *
     * @param account     account of the bidList
     * @param type        type of the BidList
     * @param bidQuantity numbers of bid
     */
    public BidListDTO(final String account, final String type, final BigDecimal bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
