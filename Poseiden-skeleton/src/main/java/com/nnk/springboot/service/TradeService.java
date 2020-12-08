package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.util.DTOConverter;
import com.nnk.springboot.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService implements ITradeService {

    private static final Logger LOGGER = LogManager.getLogger(TradeService.class);

    private final TradeRepository tradeRepository;

    private final DTOConverter dtoConverter;

    private final ModelConverter modelConverter;

    @Autowired
    public TradeService(final TradeRepository tradeRepository, final DTOConverter dtoConverter,
                        final ModelConverter modelConverter) {
        this.tradeRepository = tradeRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    public TradeDTO addTrade(final TradeDTO tradeDTO) {
        LOGGER.debug("Inside TradeService.addTrade");

        Trade trade = modelConverter.toTrade(tradeDTO);
        Trade tradeSaved = tradeRepository.save(trade);

        return dtoConverter.toTradeDTO(tradeSaved);
    }

    public TradeDTO updateTrade(final int tradeId, final TradeDTO tradeDTO) {
        LOGGER.debug("Inside TradeService.updateTrade");

        tradeDTO.setId(tradeId);
        Trade trade = modelConverter.toTrade(tradeDTO);
        Trade tradeUpdated = tradeRepository.save(trade);

        return dtoConverter.toTradeDTO(tradeUpdated);
    }

    public void deleteTrade(final int tradeId) {
        LOGGER.debug("Inside TradeService.deleteTrade");

        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() ->
                new ResourceNotFoundException("No trade registered with this id"));

        tradeRepository.delete(trade);
    }

    public TradeDTO getTradeById(final int tradeId) {
        LOGGER.debug("Inside TradeService.getTradeById");

        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() ->
                new ResourceNotFoundException("No trade registered with this id"));

        return dtoConverter.toTradeDTO(trade);
    }

    public List<TradeDTO> getAllTrade() {
        LOGGER.debug("Inside TradeService.getAllTrade");

        List<Trade> trades = tradeRepository.findAll();
        List<TradeDTO> tradeList = new ArrayList<>();

        for (Trade trade : trades) {
            TradeDTO tradeDTO = dtoConverter.toTradeDTO(trade);
            tradeList.add(tradeDTO);
        }

        return tradeList;
    }
}
