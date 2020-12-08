package com.nnk.springboot.service;

import com.nnk.springboot.dto.TradeDTO;

import java.util.List;

public interface ITradeService {

    TradeDTO addTrade(final TradeDTO trade);

    TradeDTO updateTrade(final int tradeId, final TradeDTO trade);

    void deleteTrade(final int tradeId);

    TradeDTO getTradeById(final int tradeId);

    List<TradeDTO> getAllTrade();
}

