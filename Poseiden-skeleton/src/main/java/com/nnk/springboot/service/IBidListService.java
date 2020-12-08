package com.nnk.springboot.service;

import com.nnk.springboot.dto.BidListDTO;

import java.util.List;

public interface IBidListService {

    BidListDTO addBidList(final BidListDTO bidList);

    BidListDTO updateBidList(final int bidListId, final BidListDTO bidList);

    void deleteBidList(final int bidListId);

    BidListDTO getBidListById(final int bidListId);

    List<BidListDTO> getAllBidList();
}


