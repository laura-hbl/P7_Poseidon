package com.nnk.springboot.domain;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Permits the storage and retrieving data of a trade.
 *
 * @author Laura Habdul
 */
@Entity
@Table(name = "trade")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class Trade {

    /**
     * Id of the trade.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * Account of the trade.
     */
    private String account;

    /**
     * Type of the trade.
     */
    private String type;

    /**
     * Buy quantity of the trade.
     */
    @Column(name = "buy_quantity")
    private BigDecimal buyQuantity;

    /**
     * Sell quantity of the trade.
     */
    @Column(name = "sell_quantity")
    private BigDecimal sellQuantity;

    /**
     * Buy price of the trade.
     */
    @Column(name = "buy_price")
    private BigDecimal buyPrice;

    /**
     * Sell price of the trade.
     */
    @Column(name = "sell_price")
    private BigDecimal sellPrice;

    /**
     * Security of the trade.
     */
    private String security;

    /**
     * Status of the trade.
     */
    private String status;

    /**
     * Trader of the trade.
     */
    private String trader;

    /**
     * Benchmark of the trade.
     */
    private String benchmark;

    /**
     * Book of the trade.
     */
    private String book;

    /**
     * Creation name of the trade.
     */
    @Column(name = "creation_name")
    private String creationName;

    /**
     * Date and time of the trade creation.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Revision name of the trade.
     */
    @Column(name = "revision_name")
    private String revisionName;

    /**
     * Date and time of the trade revision.
     */
    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    /**
     * Deal name of the trade.
     */
    @Column(name = "deal_name")
    private String dealName;

    /**
     * Deal type of the trade.
     */
    @Column(name = "deal_type")
    private String dealType;

    /**
     * The source list id of the trade.
     */
    @Column(name = "source_list_id")
    private String sourceListId;

    /**
     * Side of the trade.
     */
    private String side;

    /**
     * Constructor of class Trade.
     * Initialize account, type and buyQuantity.
     *
     * @param account     account of the trade
     * @param type        type of the trade
     * @param buyQuantity buy quantity of the trade.
     */
    public Trade(String account, String type, BigDecimal buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}