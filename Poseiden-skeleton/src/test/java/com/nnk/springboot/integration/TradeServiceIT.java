package com.nnk.springboot.integration;

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.TradeService;
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
public class TradeServiceIT {

    @Autowired
    private TradeService tradeService;

    @Test
    @Tag("AddTrade")
    @DisplayName("If trade is not registered, when addTrade, then return trade saved")
    public void givenAnUnRegisteredTrade_whenAddTrade_thenTradeSavedShouldBeReturned() {
        TradeDTO tradeDTO = new TradeDTO( "account3", "type3", BigDecimal.TEN);

        TradeDTO tradeSaved = tradeService.addTrade(tradeDTO);

        assertNotNull(tradeSaved);
        assertThat(tradeSaved.getAccount()).isEqualTo("account3");
    }

    @Test
    @Tag("UpdateTrade")
    @DisplayName("Given an trade to update, when updateTrade, then return trade updated")
    public void givenAnTradeToUpdate_whenUpdateTrade_thenTradeUpdatedShouldBeReturned() {
        TradeDTO tradeToUpdate = new TradeDTO( "account1", "type1", BigDecimal.valueOf(470));

        TradeDTO tradeUpdated = tradeService.updateTrade(1, tradeToUpdate);

        assertNotNull(tradeUpdated);
        assertThat(tradeUpdated.getBuyQuantity()).isEqualTo(BigDecimal.valueOf(470));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateTrade")
    @DisplayName("If Trade id cant be found, when updateTrade, then throw ResourceNotFoundException")
    public void givenUnFoundTradeId_whenUpdateTrade_thenResourceNotFoundExceptionIsThrown() {
        TradeDTO tradeToUpdate = new TradeDTO( "account1", "type1", BigDecimal.valueOf(470));

        tradeService.updateTrade(6, tradeToUpdate);
    }

    @Test
    @Tag("DeleteTrade")
    @DisplayName("Given an trade to delete, when deleteTrade, then trade should be delete successfully")
    public void givenAnTradeId_whenDeleteTrade_thenTradeShouldBeDeleteSuccessfully() {
        tradeService.deleteTrade(2);

        assertThrows(ResourceNotFoundException.class, () -> { tradeService.deleteTrade(2);
        });
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteTrade")
    @DisplayName("If trade id cant be found, when deleteTrade, then throw ResourceNotFoundException")
    public void givenUnFoundTradeId_whenDeleteTrade_thenResourceNotFoundExceptionIsThrown() {
        tradeService.deleteTrade(6);
    }

    @Test
    @Tag("GetTradeById")
    @DisplayName("Given an trade id, when getTradeById, then expected trade should be returned")
    public void givenAnTradeId_whenGetTradeById_thenExpectedTradeShouldBeReturned() {
        TradeDTO trade = tradeService.getTradeById(2);

        assertNotNull(trade);
        assertThat(trade.getAccount()).isEqualTo("account2");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetTradeById")
    @DisplayName("If trade id cant be found, when getTradeById, then throw ResourceNotFoundException")
    public void givenUnFoundTradeId_whenGetTradeById_thenResourceNotFoundExceptionIsThrown() {
        tradeService.getTradeById(6);
    }

    @Test
    @Tag("GetAllTrade")
    @DisplayName("When getAllTrade, then trade list should be returned")
    public void whenGetAllTrade_thenTradeListShouldBeReturned() {
        List<TradeDTO> trades = tradeService.getAllTrade();

        assertNotNull(trades);
        assertThat(trades.size()).isEqualTo(2);
    }
}
