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

/**
 * Contains methods that allow interaction between Trade business logic and Trade repository.
 *
 * @author Laura Habdul
 */
@Service
public class TradeService implements ITradeService {

    /**
     * CurvePointService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(TradeService.class);

    /**
     * TradeRepository instance.
     */
    private final TradeRepository tradeRepository;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * Constructor of class TradeService.
     * Initialize tradeRepository, dtoConverter and modelConverter.
     *
     * @param tradeRepository TradeRepository instance
     * @param dtoConverter    DTOConverter instance
     * @param modelConverter  ModelConverter instance
     */
    @Autowired
    public TradeService(final TradeRepository tradeRepository, final DTOConverter dtoConverter,
                        final ModelConverter modelConverter) {
        this.tradeRepository = tradeRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    /**
     * Converts the tradeDTO object to be saved to a Trade Model object, saved it to database by calling
     * TradeRepository's save method. Then, converts the saved trade to a TradeDTO object.
     *
     * @param tradeDTO the trade to be added
     * @return The trade saved converted to a TradeDTO object
     */
    public TradeDTO addTrade(final TradeDTO tradeDTO) {
        LOGGER.debug("Inside TradeService.addTrade");

        Trade trade = modelConverter.toTrade(tradeDTO);
        Trade tradeSaved = tradeRepository.save(trade);

        return dtoConverter.toTradeDTO(tradeSaved);
    }

    /**
     * Checks if the given trade to update is registered by calling TradeRepository's findById method, if so trade found
     * is updated, then saved to database by calling TradeRepository's save method and converted to a TradeDTO object.
     * Else, ResourceNotFoundException is thrown.
     *
     * @param tradeId  id of the trade to be updated
     * @param tradeDTO the trade to be updated
     * @return The trade updated converted to a TradeDTO object
     */
    public TradeDTO updateTrade(final int tradeId, final TradeDTO tradeDTO) {
        LOGGER.debug("Inside TradeService.updateTrade");

        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() ->
                new ResourceNotFoundException("No trade registered with this id"));

        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());

        Trade tradeUpdated = tradeRepository.save(trade);

        return dtoConverter.toTradeDTO(tradeUpdated);
    }

    /**
     * Checks if the given trade to delete is registered by calling TradeRepository's findById method, if so trade found
     * is deleted by calling TradeRepository's delete method. Else, ResourceNotFoundException is thrown.
     *
     * @param tradeId id of the trade to be deleted
     */
    public void deleteTrade(final int tradeId) {
        LOGGER.debug("Inside TradeService.deleteTrade");

        tradeRepository.findById(tradeId).orElseThrow(() ->
                new ResourceNotFoundException("No trade registered with this id"));

        tradeRepository.deleteById(tradeId);
    }

    /**
     * Calls TradeRepository's findById method to retrieves the trade with the given id and checks if trade exists in
     * database, if not ResourceNotFoundException is thrown. Then converts the found Trade to TradeDTO object.
     *
     * @param tradeId id of the trade to be found
     * @return The trade found converted to an TradeDTO object
     */
    public TradeDTO getTradeById(final int tradeId) {
        LOGGER.debug("Inside TradeService.getTradeById");

        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() ->
                new ResourceNotFoundException("No trade registered with this id"));

        return dtoConverter.toTradeDTO(trade);
    }

    /**
     * Retrieves all trades by calling TradeRepository's findAll() method and each trade from the list is
     * converted to an TradeDTO object and added to an ArrayList.
     *
     * @return The trade list
     */
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
