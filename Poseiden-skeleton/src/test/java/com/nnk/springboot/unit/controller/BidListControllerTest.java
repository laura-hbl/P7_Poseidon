package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.security.jwt.JwtUtils;
import com.nnk.springboot.service.BidListService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @MockBean
    private BidListService bidListService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private BidListDTO bidList1DTO;

    private BidListDTO bidList2DTO;

    @BeforeEach
    public void setUp() {
        bidList1DTO = new BidListDTO(1, "account1", "type1", BigDecimal.TEN);
        bidList2DTO = new BidListDTO(2, "account2", "type2", BigDecimal.TEN);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("ShowBidList")
    @DisplayName("When showBidList request, then display bidList list")
    public void whenShowBidListRequest_thenDisplayBidListList() throws Exception {
        when(bidListService.getAllBidList()).thenReturn(Arrays.asList(bidList1DTO, bidList2DTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk());

        verify(bidListService).getAllBidList();
    }

    @Test
    @Tag("AddBidListForm")
    @DisplayName("When addBidListForm request, then display BidList add form")
    public void whenAddBidListFormRequest_thenDisplayBidListAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(model().attributeExists("bidListDTO"))
                .andExpect(view().name("bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given a valid BidList to add, when validate request, then redirect to bidList list page")
    void givenAValidBidListToAdd_whenValidate_thenRedirectToBidListListPage() throws Exception {
        when(bidListService.addBidList(any(BidListDTO.class))).thenReturn(any(BidListDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                .sessionAttr("BidListDTO", bidList1DTO)
                .param("account", bidList1DTO.getAccount())
                .param("type", bidList1DTO.getType())
                .param("bidQuantity", bidList1DTO.getBidQuantity().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).addBidList(any(BidListDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Give empty type field, when validate request, then return error message and bidList add form")
    void givenAnEmptyBidListNameField_whenValidate_thenReturnErrorMessageAndBidListAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                .sessionAttr("bidListDTO", bidList1DTO)
                .param("account", bidList1DTO.getAccount())
                .param("type", "")
                .param("bidQuantity", bidList1DTO.getBidQuantity().toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Type is mandatory");
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given negative buy quantity, when validate request, then return error message and bidList add form")
    void givenNegativeBuyQuantity_whenValidate_thenReturnErrorMessageAndBidListAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                .sessionAttr("BidListDTO", bidList1DTO)
                .param("account", bidList1DTO.getAccount())
                .param("type", "")
                .param("bidQuantity", BigDecimal.valueOf(-1).toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("The value must be positive");
        verify(bidListService, times(0)).addBidList(any(BidListDTO.class));
    }

    @Test
    @Tag("ShowUpdateForm")
    @DisplayName("When showUpdateForm request, then display BidList update form")
    public void whenShowUpdateFormRequest_thenDisplayBidListUpdateForm() throws Exception {
        when(bidListService.getBidListById(1)).thenReturn(bidList1DTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(model().attributeExists("bidListDTO"))
                .andExpect(view().name("bidList/update"))
                .andExpect(status().isOk());

        verify(bidListService).getBidListById(1);
    }

    @Test
    @Tag("UpdateBidList")
    @DisplayName("Given a valid bidList to update, when updateBidList request, then redirect to bidList list page")
    void givenAValidBidListToUpdate_whenUpdateBidList_thenRedirectToBidListListPage() throws Exception {
        BidListDTO bidListUpdateDTO = new BidListDTO( "account", "type", BigDecimal.ONE);
        when(bidListService.updateBidList(1, bidList1DTO)).thenReturn(bidListUpdateDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
                .sessionAttr("BidListDTO", bidList1DTO)
                .param("account", bidList1DTO.getAccount())
                .param("type", bidList1DTO.getType())
                .param("bidQuantity", bidList1DTO.getBidQuantity().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).updateBidList(anyInt(), any(BidListDTO.class));
    }

    @Test
    @Tag("UpdateBidList")
    @DisplayName("Given invalid type, when updateBidList request, then return error message and bidList update form")
    void givenInvalidType_whenUpdateBidList_thenReturnErrorMessageAndBidListUpdateForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
                .sessionAttr("BidListDTO", bidList1DTO)
                .param("account", bidList1DTO.getAccount())
                .param("type", "typetypetypetypetypetypetypetypetypetype")
                .param("bidQuantity", bidList1DTO.getBidQuantity().toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/update"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("The maximum length for type is 30 characters");
        verify(bidListService, times(0)).updateBidList(anyInt(), any(BidListDTO.class));
    }

    @Test
    @Tag("DeleteBidList")
    @DisplayName("Given a bidList to delete, when deleteBidList request, then redirect to BidList list page")
    void givenABidListToDelete_whenUpdateBidList_thenRedirectToBidListListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1"))
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).deleteBidList(1);
    }
}
