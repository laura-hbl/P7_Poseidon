package com.nnk.springboot.service;

import com.nnk.springboot.dto.TradeDTO;

import java.util.List;

/**
 * TradeService interface.
 *
 * @author Laura Habdul
 */
public interface ITradeService {

    /**
     * Adds a new trade in database.
     *
     * @param trade the trade to be added
     * @return The trade saved converted to a TradeDTO object
     */
    TradeDTO addTrade(final TradeDTO trade);

    /**
     * Updates an trade based on the given id.
     *
     * @param tradeId id of the trade to be updated
     * @param trade   the trade to be updated
     * @return The trade updated converted to a TradeDTO object
     */
    TradeDTO updateTrade(final int tradeId, final TradeDTO trade);

    /**
     * Deletes an trade based on the given id.
     *
     * @param tradeId id of the trade to be deleted
     */
    void deleteTrade(final int tradeId);

    /**
     * Retrieves an trade based on the given id.
     *
     * @param tradeId id of the trade to be found
     * @return The trade retrieved converted to a TradeDTO object
     */
    TradeDTO getTradeById(final int tradeId);

    /**
     * Retrieves the trade list.
     *
     * @return The trade list retrieved where each Trade is converted to a TradeDTO object
     */
    List<TradeDTO> getAllTrade();
}

