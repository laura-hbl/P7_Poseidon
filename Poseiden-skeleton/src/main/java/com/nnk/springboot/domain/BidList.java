package com.nnk.springboot.domain;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Permits the storage and retrieving data of a bidList.
 *
 * @author Laura Habdul
 */
@Entity
@Table(name = "bid_list")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class BidList {

    /**
     * Id of the bidList.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Account of the bidList.
     */
    private String account;

    /**
     * Type of the BidList.
     */
    private String type;

    /**
     * Numbers of bid.
     */
    @Column(name = "bid_quantity")
    private BigDecimal bidQuantity;

    /**
     * Numbers of ask.
     */
    @Column(name = "ask_quantity")
    private BigDecimal askQuantity;

    /**
     * Bid of the bidList.
     */
    private BigDecimal bid;

    /**
     * Ask of the bidList.
     */
    private BigDecimal ask;

    /**
     * Benchmark of the bidList.
     */
    private String benchmark;

    /**
     * Date and time of the BidList.
     */
    @Column(name = "bid_list_date")
    private LocalDateTime bidListDate;

    /**
     * Commentary of the bidList.
     */
    private String commentary;

    /**
     * Security of the bidList.
     */
    private String security;

    /**
     * Status of the bidList.
     */
    private String status;

    /**
     * Trader of the bidList.
     */
    private String trader;

    /**
     * Book of the bidList.
     */
    private String book;

    /**
     * Name of bidList creation.
     */
    @Column(name = "creation_name")
    private String creationName;

    /**
     * Date and time of bidList creation.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Name of revision.
     */
    @Column(name = "revision_name")
    private String revisionName;

    /**
     * Date and time of bidList revision.
     */
    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    /**
     * Name of the deal.
     */
    @Column(name = "deal_name")
    private String dealName;

    /**
     * Type of the deal.
     */
    @Column(name = "deal_type")
    private String dealType;

    /**
     * Id of the source list.
     */
    @Column(name = "source_list_id")
    private String sourceListId;

    /**
     * Side of the bidList.
     */
    private String side;

    /**
     * Constructor of class BidList.
     * Initialize id, account, type and bidQuantity.
     *
     * @param id id of the bidList
     * @param account account of the bidList
     * @param type type of the bidList
     * @param bidQuantity quantity of bid
     */
    public BidList(Integer id, String account, String type, BigDecimal bidQuantity) {
        this.id = id;
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
