package com.nnk.springboot.domain;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bid_list")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String account;

    private String type;

    @Column(name = "bid_quantity")
    private BigDecimal bidQuantity;

    @Column(name = "ask_quantity")
    private BigDecimal askQuantity;

    private BigDecimal bid;

    private BigDecimal ask;

    private String benchmark;

    @Column(name = "bid_list_date")
    private LocalDateTime bidListDate;

    private String commentary;

    private String security;

    private String status;

    private String trader;

    private String book;

    @Column(name = "creation_name")
    private String creationName;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "revision_name")
    private String revisionName;

    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    @Column(name = "deal_name")
    private String dealName;

    @Column(name = "deal_type")
    private String dealType;

    @Column(name = "source_list_id")
    private String sourceListId;

    private String side;

}
