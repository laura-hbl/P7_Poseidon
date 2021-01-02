package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.service.TradeService;
import com.nnk.springboot.util.DTOConverter;
import com.nnk.springboot.util.ModelConverter;
import com.nnk.springboot.exception.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    private static Trade trade1;

    private static Trade trade2;

    private static TradeDTO trade1DTO;

    private static TradeDTO trade2DTO;

    private static List<TradeDTO> tradeListDTO;

    @Before
    public void setUp() {
        trade1DTO = new TradeDTO(1, "account1", "type1", BigDecimal.TEN);
        trade2DTO = new TradeDTO(2, "account2", "type2", BigDecimal.TEN);
        trade1 = new Trade(1, "account1", "type1", BigDecimal.TEN, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null);
        trade2 = new Trade(2, "account2", "type2", BigDecimal.TEN, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null);
        tradeListDTO = Arrays.asList(trade1DTO, trade2DTO);
    }

    @Test
    @Tag("AddTrade")
    @DisplayName("Given a trade to save, when addTrade, then Trade should be saved correctly")
    public void givenATrade_whenAddTrade_thenTradeShouldBeSavedCorrectly() {
        TradeDTO tradeToAddDTO = new TradeDTO("account1", "type1", BigDecimal.TEN);
        Trade tradeToAdd = new Trade("account1", "type1", BigDecimal.TEN);
        when(modelConverter.toTrade(any(TradeDTO.class))).thenReturn(tradeToAdd);
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade1);
        when(dtoConverter.toTradeDTO(any(Trade.class))).thenReturn(trade1DTO);

        TradeDTO TradeSaved = tradeService.addTrade(tradeToAddDTO);

        assertThat(TradeSaved).isEqualToComparingFieldByField(trade1DTO);
        InOrder inOrder = inOrder(tradeRepository, dtoConverter, modelConverter);
        inOrder.verify(modelConverter).toTrade(any(TradeDTO.class));
        inOrder.verify(tradeRepository).save(any(Trade.class));
        inOrder.verify(dtoConverter).toTradeDTO(any(Trade.class));
    }

    @Test
    @Tag("UpdateTrade")
    @DisplayName("Given a registered Trade, when updateTrade, then Trade should be updated correctly")
    public void givenATradeToUpdate_whenUpdateTrade_thenTradeShouldBeUpdateCorrectly() {
        TradeDTO trade1DTOUpdated = new TradeDTO(1, "account1", "type1", BigDecimal.valueOf(300));
        Trade trade1Updated = new Trade(1, "account1", "type1", BigDecimal.valueOf(300), null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null);
        when(tradeRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(trade1));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade1Updated);
        when(dtoConverter.toTradeDTO(any(Trade.class))).thenReturn(trade1DTOUpdated);

        TradeDTO result = tradeService.updateTrade(1, new TradeDTO( "account1", "type1",
                BigDecimal.valueOf(300)));

        assertThat(result).isEqualTo(trade1DTOUpdated);
        InOrder inOrder = inOrder(tradeRepository, dtoConverter);
        inOrder.verify(tradeRepository).findById(anyInt());
        inOrder.verify(tradeRepository).save(any(Trade.class));
        inOrder.verify(dtoConverter).toTradeDTO(any(Trade.class));
    }

    @Test
    @Tag("DeleteTrade")
    @DisplayName("Given Trade Id, when deleteTrade, then delete process should be done in correct order")
    public void givenATradeId_whenDeleteTrade_thenDeletingShouldBeDoneInCorrectOrder() {
        when(tradeRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(trade1));

        tradeService.deleteTrade(anyInt());

        InOrder inOrder = inOrder(tradeRepository);
        inOrder.verify(tradeRepository).findById(anyInt());
        inOrder.verify(tradeRepository).deleteById(anyInt());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteTrade")
    @DisplayName("If Trade can't be found, when deleteTrade, then throw ResourceNotFoundException")
    public void givenUnFoundTrade_whenDeleteTrade_thenResourceNotFoundExceptionIsThrown() {
        when(tradeRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        tradeService.deleteTrade(anyInt());
    }

    @Test
    @Tag("GetTradeById")
    @DisplayName("Given an Trade id, when getTradeById, then expected Trade should be returned correctly")
    public void givenAnTradeId_whenGetTradeById_thenExpectedTradeShouldBeReturnCorrectly() {
        when(tradeRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(trade1));
        when(dtoConverter.toTradeDTO(any(Trade.class))).thenReturn(trade1DTO);

        TradeDTO TradeFound = tradeService.getTradeById(1);

        assertThat(TradeFound).isEqualToComparingFieldByField(trade1DTO);
        InOrder inOrder = inOrder(tradeRepository, dtoConverter);
        inOrder.verify(tradeRepository).findById(anyInt());
        inOrder.verify(dtoConverter).toTradeDTO(any(Trade.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetTradeById")
    @DisplayName("If trade can't be found, when getTradeById, then throw ResourceNotFoundException")
    public void givenUnFoundTrade_whenGetTradeById_thenResourceNotFoundExceptionIsThrown() {
        when(tradeRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        tradeService.getTradeById(1);
    }

    @Test
    @Tag("GetAllTrade")
    @DisplayName("Given an Trade list, when getAllTrade, then result should match expected Trade list")
    public void givenATradeList_whenGetAllTrade_thenReturnExpectedTradeList() {
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade1, trade2));

        when(dtoConverter.toTradeDTO(trade1)).thenReturn(trade1DTO);
        when(dtoConverter.toTradeDTO(trade2)).thenReturn(trade2DTO);

        List<TradeDTO> result = tradeService.getAllTrade();

        assertThat(result).isEqualTo(tradeListDTO);
        InOrder inOrder = inOrder(tradeRepository, dtoConverter);
        inOrder.verify(tradeRepository).findAll();
        inOrder.verify(dtoConverter).toTradeDTO(trade1);
        inOrder.verify(dtoConverter).toTradeDTO(trade2);
    }
}
