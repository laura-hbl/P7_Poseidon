package com.nnk.springboot.dto;

import com.nnk.springboot.constant.TradeConstraints;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class TradeDTO {

    private Integer id;

    @NotEmpty(message = "Account is mandatory")
    @Length(max = TradeConstraints.ACCOUNT_MAX_SIZE, message = "The maximum length for account is 30 characters")
    private String account;

    @NotEmpty(message = "Type is mandatory")
    @Length(max = TradeConstraints.TYPE_MAX_SIZE, message = "The maximum length for type is 30 characters")
    private String type;

    @Min(value = 0, message = "The value must be positive")
    private BigDecimal buyQuantity;

    public TradeDTO(final String account, final String type, final BigDecimal buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}
