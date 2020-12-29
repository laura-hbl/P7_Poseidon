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

/**
 * Contains methods that allow interaction between BidList business logic and BidList repository.
 *
 * @author Laura Habdul
 */
@Service
public class BidListService implements IBidListService {

    /**
     * BidListService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(BidListService.class);

    /**
     * BidListRepository instance.
     */
    private final BidListRepository bidListRepository;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * Constructor of class BidListService.
     * Initialize bidListRepository, dtoConverter and modelConverter.
     *
     * @param bidListRepository BidListRepository instance
     * @param dtoConverter      DTOConverter instance
     * @param modelConverter    ModelConverter instance
     */
    @Autowired
    public BidListService(final BidListRepository bidListRepository, final DTOConverter dtoConverter,
                          final ModelConverter modelConverter) {
        this.bidListRepository = bidListRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    /**
     * Converts the bidListDTO object to be saved to a BidList Model object, saved it to database by calling
     * BidListRepository's save method. Then, converts the saved bidList to a BidListDTO object.
     *
     * @param bidListDTO the bidList to be added
     * @return The bidList saved converted to a BidListDTO object
     */
    public BidListDTO addBidList(final BidListDTO bidListDTO) {
        LOGGER.debug("Inside BidListService.updateBidList");

        BidList bidList = modelConverter.toBidList(bidListDTO);
        BidList bidListUpdated = bidListRepository.save(bidList);

        return dtoConverter.toBidListDTO(bidListUpdated);
    }

    /**
     * Checks if the given bidList to update is registered by calling BidListRepository's findById method, if so bidList
     * found is updated, then saved to database by calling BidListRepository's save method and converted to a BidListDTO
     * object. Else, ResourceNotFoundException is thrown.
     *
     * @param bidListId  id of the bidList to be updated
     * @param bidListDTO the bidList to be updated
     * @return The bidList updated converted to a BidListDTO object
     */
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

    /**
     * Checks if the given bidList to delete is registered by calling BidListRepository's findById method, if so bidList
     * found is deleted by calling BidListRepository's delete method. Else, ResourceNotFoundException is thrown.
     *
     * @param bidListId id of the bidList to be deleted
     */
    public void deleteBidList(final int bidListId) {
        LOGGER.debug("Inside BidListService.deleteBidList");

        bidListRepository.findById(bidListId).orElseThrow(() ->
                new ResourceNotFoundException("No bidList registered with this id"));

        bidListRepository.deleteById(bidListId);
    }

    /**
     * Calls BidListRepository's findById method to retrieves the bidList with the given id and checks if bidList exists in
     * database, if not ResourceNotFoundException is thrown. Then converts the found BidList to BidListDTO object.
     *
     * @param bidListId id of the bidList to be found
     * @return The bidList found converted to an BidListDTO object
     */
    public BidListDTO getBidListById(final int bidListId) {
        LOGGER.debug("Inside BidListService.getBidListById");

        BidList bidList = bidListRepository.findById(bidListId).orElseThrow(() ->
                new ResourceNotFoundException("No bidList registered with this id"));

        return dtoConverter.toBidListDTO(bidList);
    }

    /**
     * Retrieves all bidLists by calling BidListRepository's findAll() method and each bidList from the list is
     * converted to an BidListDTO object and added to an ArrayList.
     *
     * @return The bidList list
     */
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
