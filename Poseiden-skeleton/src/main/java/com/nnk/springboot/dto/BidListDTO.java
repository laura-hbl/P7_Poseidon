package com.nnk.springboot.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class BidListDTO {

    private Integer id;

    private String account;

    private String type;

    private BigDecimal bidQuantity;
}
