package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.util.DTOConverter;
import com.nnk.springboot.util.ModelConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BidListServiceTest {

    @InjectMocks
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    private static BidList bidList1;

    private static BidList bidList2;

    private static BidListDTO bidList1DTO;

    private static BidListDTO bidList2DTO;

    private static List<BidListDTO> listOfBidListDTO;

    @Before
    public void setUp() {
        bidList1DTO = new BidListDTO(1, "account1", "type1", BigDecimal.TEN);
        bidList2DTO = new BidListDTO(2, "account2", "type2", BigDecimal.TEN);
        bidList1 = new BidList(1, "account1", "type1", BigDecimal.TEN, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);
        bidList2 = new BidList(1, "account2", "type2", BigDecimal.TEN, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);
        listOfBidListDTO = Arrays.asList(bidList1DTO, bidList2DTO);
    }

    @Test
    @Tag("AddBidList")
    @DisplayName("Given a bidList to save, when addBidList, then bidList should be saved correctly")
    public void givenABidList_whenAddBidList_thenBidListShouldBeSavedCorrectly() {
        BidListDTO bidListToAddDTO = new BidListDTO("account1", "type1", BigDecimal.TEN);
        BidList bidListToAdd = new BidList("account1", "type1", BigDecimal.TEN);

        when(modelConverter.toBidList(any(BidListDTO.class))).thenReturn(bidListToAdd);
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList1);
        when(dtoConverter.toBidListDTO(any(BidList.class))).thenReturn(bidList1DTO);

        BidListDTO bidListAdded = bidListService.addBidList(bidListToAddDTO);

        assertThat(bidListAdded).isEqualToComparingFieldByField(bidList1DTO);
        InOrder inOrder = inOrder(bidListRepository, dtoConverter, modelConverter);
        inOrder.verify(modelConverter).toBidList(any(BidListDTO.class));
        inOrder.verify(bidListRepository).save(any(BidList.class));
        inOrder.verify(dtoConverter).toBidListDTO(any(BidList.class));
    }

    @Test
    @Tag("UpdateBidList")
    @DisplayName("Given a registered bidList, when updateBidList, then bidList should be updated correctly")
    public void givenABidListToUpdate_whenUpdateBidList_thenBidListShouldBeUpdateCorrectly() {
        BidList bidUpdated = new BidList(1,"account1", "type1", BigDecimal.valueOf(100), null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);
        BidListDTO bidUpdatedDTO = new BidListDTO(1,"account1", "type1", BigDecimal.valueOf(100));
        when(bidListRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(bidList1));
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidUpdated);
        when(dtoConverter.toBidListDTO(any(BidList.class))).thenReturn(bidUpdatedDTO);

        BidListDTO result = bidListService.updateBidList(1, new BidListDTO("account1", "type1",
                BigDecimal.valueOf(100)));

        assertThat(result).isEqualTo(bidUpdatedDTO);
        InOrder inOrder = inOrder(bidListRepository, dtoConverter);
        inOrder.verify(bidListRepository).findById(anyInt());
        inOrder.verify(bidListRepository).save(any(BidList.class));
        inOrder.verify(dtoConverter).toBidListDTO(any(BidList.class));
    }

    @Test
    @Tag("DeleteBidList")
    @DisplayName("Given a bidList Id, when deleteBidList, then delete process should be done in correct order")
    public void givenABidListId_whenDeleteBidList_thenDeletingShouldBeDoneInCorrectOrder() {
        when(bidListRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(bidList1));

        bidListService.deleteBidList(anyInt());

        InOrder inOrder = inOrder(bidListRepository);
        inOrder.verify(bidListRepository).findById(anyInt());
        inOrder.verify(bidListRepository).deleteById(anyInt());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteBidList")
    @DisplayName("If bidList can't be found, when deleteBidList, then throw ResourceNotFoundException")
    public void givenUnFoundBidList_whenDeleteBidList_thenResourceNotFoundExceptionIsThrown() {
        when(bidListRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        bidListService.deleteBidList(anyInt());
    }

    @Test
    @Tag("GetBidListById")
    @DisplayName("Given a bidList id, when getBidListById, then expected bidList should be returned correctly")
    public void givenABidListId_whenGetBidListById_thenExpectedBidListShouldBeReturnCorrectly() {
        when(bidListRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(bidList1));
        when(dtoConverter.toBidListDTO(any(BidList.class))).thenReturn(bidList1DTO);

        BidListDTO bidListFound = bidListService.getBidListById(1);

        assertThat(bidListFound).isEqualToComparingFieldByField(bidList1DTO);
        InOrder inOrder = inOrder(bidListRepository, dtoConverter);
        inOrder.verify(bidListRepository).findById(anyInt());
        inOrder.verify(dtoConverter).toBidListDTO(any(BidList.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetBidListById")
    @DisplayName("If bidList can't be found, when getBidListById, then throw ResourceNotFoundException")
    public void givenUnFoundBidList_whenGetBidListById_thenResourceNotFoundExceptionIsThrown() {
        when(bidListRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        bidListService.getBidListById(1);
    }

    @Test
    @Tag("GetAllBidList")
    @DisplayName("Given an bidList list, when getAllBidList, then result should match expected bidList list")
    public void givenABidLisBidList_whenGetAllBidList_thenReturnExpectedBidListList() {
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bidList1, bidList2));
        when(dtoConverter.toBidListDTO(bidList1)).thenReturn(bidList1DTO);
        when(dtoConverter.toBidListDTO(bidList2)).thenReturn(bidList2DTO);

        List<BidListDTO> result = bidListService.getAllBidList();

        assertThat(result).isEqualTo(listOfBidListDTO);
        InOrder inOrder = inOrder(bidListRepository, dtoConverter);
        inOrder.verify(bidListRepository).findAll();
        inOrder.verify(dtoConverter).toBidListDTO(bidList1);
        inOrder.verify(dtoConverter).toBidListDTO(bidList2);
    }
}
