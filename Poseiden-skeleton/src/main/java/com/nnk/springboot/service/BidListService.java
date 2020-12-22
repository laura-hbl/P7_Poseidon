package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.util.DTOConverter;
import com.nnk.springboot.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BidListService implements IBidListService {

    private static final Logger LOGGER = LogManager.getLogger(BidListService.class);

    private final BidListRepository bidListRepository;

    private final DTOConverter dtoConverter;

    private final ModelConverter modelConverter;

    @Autowired
    public BidListService(final BidListRepository bidListRepository, final DTOConverter dtoConverter,
                             final ModelConverter modelConverter) {
        this.bidListRepository = bidListRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    public BidListDTO addBidList(final BidListDTO bidListDTO) {
        LOGGER.debug("Inside BidListService.updateBidList");

        BidList bidList = modelConverter.toBidList(bidListDTO);
        BidList bidListUpdated = bidListRepository.save(bidList);

        return dtoConverter.toBidListDTO(bidListUpdated);
    }

    public BidListDTO updateBidList(final int bidListId, final BidListDTO bidListDTO) {
        LOGGER.debug("Inside BidListService.updateBidList");

        BidList bidList = bidListRepository.findById(bidListId).orElseThrow(() ->
                new ResourceNotFoundException("No bidList registered with this id"));

        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(bidListDTO.getBidQuantity());

        BidList bidListUpdated = bidListRepository.save(bidList);

        return dtoConverter.toBidListDTO(bidListUpdated);
    }

    public void deleteBidList(final int bidListId) {
        LOGGER.debug("Inside BidListService.deleteBidList");

        bidListRepository.findById(bidListId).orElseThrow(() ->
                new ResourceNotFoundException("No bidList registered with this id"));

        bidListRepository.deleteById(bidListId);
    }

    public BidListDTO getBidListById(final int bidListId) {
        LOGGER.debug("Inside BidListService.getBidListById");

        BidList bidList = bidListRepository.findById(bidListId).orElseThrow(() ->
                new ResourceNotFoundException("No bidList registered with this id"));

        return dtoConverter.toBidListDTO(bidList);
    }

    public List<BidListDTO> getAllBidList() {
        LOGGER.debug("Inside BidListService.getAllBidList");

        List<BidList> bidLists = bidListRepository.findAll();
        List<BidListDTO> bidListList = new ArrayList<>();

        for (BidList bidList : bidLists) {
            BidListDTO bidListDTO = dtoConverter.toBidListDTO(bidList);
            bidListList.add(bidListDTO);
        }

        return bidListList;
    }
}
