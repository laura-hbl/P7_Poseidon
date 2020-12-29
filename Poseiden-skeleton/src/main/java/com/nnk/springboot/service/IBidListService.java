package com.nnk.springboot.service;

import com.nnk.springboot.dto.BidListDTO;

import java.util.List;

/**
 * BidListService interface.
 *
 * @author Laura Habdul
 */
public interface IBidListService {

    /**
     * Adds a new bidList in database.
     *
     * @param bidList the bidList to be added
     * @return The bidList saved converted to a BidListDTO object
     */
    BidListDTO addBidList(final BidListDTO bidList);

    /**
     * Updates an bidList based on the given id.
     *
     * @param bidListId id of the bidList to be updated
     * @param bidList   the bidList to be updated
     * @return The bidList updated converted to a BidListDTO object
     */
    BidListDTO updateBidList(final int bidListId, final BidListDTO bidList);

    /**
     * Deletes an bidList based on the given id.
     *
     * @param bidListId id of the bidList to be deleted
     */
    void deleteBidList(final int bidListId);

    /**
     * Retrieves an bidList based on the given id.
     *
     * @param bidListId id of the bidList to be found
     * @return The bidList retrieved converted to a BidListDTO object
     */
    BidListDTO getBidListById(final int bidListId);

    /**
     * Retrieves the bidList list.
     *
     * @return The bidList list retrieved where each BidList is converted to a BidListDTO object
     */
    List<BidListDTO> getAllBidList();
}
