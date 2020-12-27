package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controller.TradeController;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.security.jwt.JwtUtils;
import com.nnk.springboot.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @MockBean
    private TradeService tradeService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private TradeDTO trade1DTO;

    private TradeDTO trade2DTO;

    @BeforeEach
    public void setUp() {
        trade1DTO = new TradeDTO(1, "account1", "type1", BigDecimal.TEN);
        trade2DTO = new TradeDTO(2, "account2", "type2", BigDecimal.TEN);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("ShowTradeList")
    @DisplayName("When showTradeList request, then display trade list")
    public void whenShowTradeListRequest_thenDisplayTradeList() throws Exception {
        when(tradeService.getAllTrade()).thenReturn(Arrays.asList(trade1DTO, trade2DTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk());

        verify(tradeService).getAllTrade();
    }

    @Test
    @Tag("AddTradeForm")
    @DisplayName("When addTradeForm request, then display Trade add form")
    public void whenAddTradeFormRequest_thenDisplayTradeAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(model().attributeExists("tradeDTO"))
                .andExpect(view().name("trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given a valid Trade to add, when validate request, then redirect to trade list page")
    void givenAValidTradeToAdd_whenValidate_thenRedirectToTradeListPage() throws Exception {
        when(tradeService.addTrade(any(TradeDTO.class))).thenReturn(any(TradeDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                .sessionAttr("TradeDTO", trade1DTO)
                .param("account", trade1DTO.getAccount())
                .param("type", trade1DTO.getType())
                .param("buyQuantity", trade1DTO.getBuyQuantity().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).addTrade(any(TradeDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Give empty type field, when validate request, then return error message and trade add form")
    void givenAnEmptyTradeNameField_whenValidate_thenReturnErrorMessageAndTradeAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                .sessionAttr("tradeDTO", trade1DTO)
                .param("account", trade1DTO.getAccount())
                .param("type", "")
                .param("buyQuantity", trade1DTO.getBuyQuantity().toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Type is mandatory");
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given negative buy quantity, when validate request, then return error message and trade add form")
    void givenNegativeBuyQuantity_whenValidate_thenReturnErrorMessageAndTradeAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                .sessionAttr("TradeDTO", trade1DTO)
                .param("account", trade1DTO.getAccount())
                .param("type", "")
                .param("buyQuantity", BigDecimal.valueOf(-1).toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("The value must be positive");
        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
    }

    @Test
    @Tag("ShowUpdateForm")
    @DisplayName("When showUpdateForm request, then display Trade update form")
    public void whenShowUpdateFormRequest_thenDisplayTradeUpdateForm() throws Exception {
        when(tradeService.getTradeById(1)).thenReturn(trade1DTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/1"))
                .andExpect(model().attributeExists("tradeDTO"))
                .andExpect(view().name("trade/update"))
                .andExpect(status().isOk());

        verify(tradeService).getTradeById(1);
    }

    @Test
    @Tag("UpdateTrade")
    @DisplayName("Given a valid trade to update, when updateTrade request, then redirect to trade list page")
    void givenAValidTradeToUpdate_whenUpdateTrade_thenRedirectToTradeListPage() throws Exception {
        TradeDTO tradeUpdateDTO = new TradeDTO( "account", "type", BigDecimal.ONE);
        when(tradeService.updateTrade(1, trade1DTO)).thenReturn(tradeUpdateDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
                .sessionAttr("TradeDTO", trade1DTO)
                .param("account", trade1DTO.getAccount())
                .param("type", trade1DTO.getType())
                .param("buyQuantity", trade1DTO.getBuyQuantity().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).updateTrade(anyInt(), any(TradeDTO.class));
    }

    @Test
    @Tag("UpdateTrade")
    @DisplayName("Given invalid type, when updateTrade request, then return error message and trade update form")
    void givenInvalidType_whenUpdateTrade_thenReturnErrorMessageAndTradeUpdateForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
                .sessionAttr("TradeDTO", trade1DTO)
                .param("account", trade1DTO.getAccount())
                .param("type", "typetypetypetypetypetypetypetypetypetype")
                .param("buyQuantity", trade1DTO.getBuyQuantity().toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/update"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("The maximum length for type is 30 characters");
        verify(tradeService, times(0)).updateTrade(anyInt(), any(TradeDTO.class));
    }

    @Test
    @Tag("DeleteTrade")
    @DisplayName("Given a trade to delete, when deleteTrade request, then redirect to trade list page")
    void givenATradeToDelete_whenUpdateTrade_thenRedirectToTradeListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).deleteTrade(1);
    }
}
