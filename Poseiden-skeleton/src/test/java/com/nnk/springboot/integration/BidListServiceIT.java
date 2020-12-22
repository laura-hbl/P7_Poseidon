package com.nnk.springboot.integration;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.BidListService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BidListServiceIT {

    @Autowired
    private BidListService bidListService;

    @Test
    @Tag("AddBidList")
    @DisplayName("If bidList is not registered, when addBidList, then return bidList saved")
    public void givenAnUnRegisteredBidList_whenAddBidList_thenBidListSavedShouldBeReturned() {
        BidListDTO bidListDTO = new BidListDTO( "account3", "type3", BigDecimal.TEN);

        BidListDTO bidListSaved = bidListService.addBidList(bidListDTO);

        assertNotNull(bidListSaved);
        assertThat(bidListSaved.getAccount()).isEqualTo("account3");
    }

    @Test
    @Tag("UpdateBidList")
    @DisplayName("Given an bidList to update, when updateBidList, then return bidList updated")
    public void givenAnBidListToUpdate_whenUpdateBidList_thenBidListUpdatedShouldBeReturned() {
        BidListDTO bidListToUpdate = new BidListDTO( "account1", "type1", BigDecimal.valueOf(470));

        BidListDTO bidListUpdated = bidListService.updateBidList(1, bidListToUpdate);

        assertNotNull(bidListUpdated);
        assertThat(bidListUpdated.getBidQuantity()).isEqualTo(BigDecimal.valueOf(470));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateBidList")
    @DisplayName("If BidList id cant be found, when updateBidList, then throw ResourceNotFoundException")
    public void givenUnFoundBidListId_whenUpdateBidList_thenResourceNotFoundExceptionIsThrown() {
        BidListDTO bidListToUpdate = new BidListDTO( "account1", "type1", BigDecimal.valueOf(470));

        bidListService.updateBidList(6, bidListToUpdate);
    }

    @Test
    @Tag("DeleteBidList")
    @DisplayName("Given an bidList to delete, when deleteBidList, then bidList should be delete successfully")
    public void givenAnBidListId_whenDeleteBidList_thenBidListShouldBeDeleteSuccessfully() {
        bidListService.deleteBidList(2);

        assertThrows(ResourceNotFoundException.class, () -> { bidListService.deleteBidList(2);
        });
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteBidList")
    @DisplayName("If bidList id cant be found, when deleteBidList, then throw ResourceNotFoundException")
    public void givenUnFoundBidListId_whenDeleteBidList_thenResourceNotFoundExceptionIsThrown() {
        bidListService.deleteBidList(6);
    }

    @Test
    @Tag("GetBidListById")
    @DisplayName("Given an bidList id, when getBidListById, then expected bidList should be returned")
    public void givenAnBidListId_whenGetBidListById_thenExpectedBidListShouldBeReturned() {
        BidListDTO bidList = bidListService.getBidListById(2);

        assertNotNull(bidList);
        assertThat(bidList.getAccount()).isEqualTo("account2");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetBidListById")
    @DisplayName("If bidList id cant be found, when getBidListById, then throw ResourceNotFoundException")
    public void givenUnFoundBidListId_whenGetBidListById_thenResourceNotFoundExceptionIsThrown() {
        bidListService.getBidListById(6);
    }

    @Test
    @Tag("GetAllBidList")
    @DisplayName("When getAllBidList, then bidList list should be returned")
    public void whenGetAllBidList_thenBidListListShouldBeReturned() {
        List<BidListDTO> bidLists = bidListService.getAllBidList();

        assertNotNull(bidLists);
        assertThat(bidLists.size()).isEqualTo(2);
    }
}
