package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controller.CurvePointController;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.security.jwt.JwtUtils;
import com.nnk.springboot.service.CurvePointService;
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
@WebMvcTest(CurvePointController.class)
public class CurvePointControllerTest {

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private CurvePointDTO curvePoint1DTO;

    private CurvePointDTO curvePoint2DTO;

    @BeforeEach
    public void setUp() {
        curvePoint1DTO = new CurvePointDTO(1, 3, BigDecimal.TEN, BigDecimal.TEN);
        curvePoint2DTO = new CurvePointDTO(2, 4, BigDecimal.TEN, BigDecimal.TEN);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("ShowCurvePointList")
    @DisplayName("When showCurvePointList request, then display curvePoint list")
    public void whenShowCurvePointListRequest_thenDisplayCurvePointList() throws Exception {
        when(curvePointService.getAllCurvePoint()).thenReturn(Arrays.asList(curvePoint1DTO, curvePoint2DTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk());

        verify(curvePointService).getAllCurvePoint();
    }

    @Test
    @Tag("AddCurvePointForm")
    @DisplayName("When addCurvePointForm request, then display CurvePoint add form")
    public void whenAddCurvePointFormRequest_thenDisplayCurvePointAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(model().attributeExists("curvePointDTO"))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given a valid CurvePoint to add, when validate request, then redirect to curvePoint list page")
    void givenAValidCurvePointToAdd_whenValidate_thenRedirectToCurvePointListPage() throws Exception {
        when(curvePointService.addCurvePoint(any(CurvePointDTO.class))).thenReturn(any(CurvePointDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                .sessionAttr("curvePointDTO", curvePoint1DTO)
                .param("curveId", curvePoint1DTO.getCurveId().toString())
                .param("term", curvePoint1DTO.getTerm().toString())
                .param("value", curvePoint1DTO.getValue().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).addCurvePoint(any(CurvePointDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given empty curveId field, when validate request, then return error message and curvePoint add form")
    void givenNegativeOrderNumber_whenValidate_thenReturnErrorMessageAndCurvePointAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                .sessionAttr("curvePointDTO", curvePoint1DTO)
                .param("curveId", "")
                .param("term", curvePoint1DTO.getTerm().toString())
                .param("value", curvePoint1DTO.getValue().toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("must not be null");
        verify(curvePointService, times(0)).addCurvePoint(any(CurvePointDTO.class));
    }

    @Test
    @Tag("ShowUpdateForm")
    @DisplayName("When showUpdateForm request, then display CurvePoint update form")
    public void whenShowUpdateFormRequest_thenDisplayCurvePointUpdateForm() throws Exception {
        when(curvePointService.getCurvePointById(1)).thenReturn(curvePoint1DTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))
                .andExpect(model().attributeExists("curvePointDTO"))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(status().isOk());

        verify(curvePointService).getCurvePointById(1);
    }

    @Test
    @Tag("UpdateCurvePoint")
    @DisplayName("Given a valid curvePoint to update, when updateCurvePoint request, then redirect to curvePoint list page")
    void givenAValidCurvePointToUpdate_whenUpdateCurvePoint_thenRedirectToCurvePointListPage() throws Exception {
        CurvePointDTO curvePointUpdateDTO = new CurvePointDTO(1, 3, BigDecimal.ONE, BigDecimal.TEN);
        when(curvePointService.updateCurvePoint(1, curvePoint1DTO)).thenReturn(curvePointUpdateDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                .sessionAttr("curvePointDTO", curvePoint1DTO)
                .param("curveId", curvePoint1DTO.getCurveId().toString())
                .param("term", curvePoint1DTO.getTerm().toString())
                .param("value", curvePoint1DTO.getValue().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
    }

    @Test
    @Tag("UpdateCurvePoint")
    @DisplayName("Given negative value, when updateCurvePoint request, then return error message and curvePoint update form")
    void givenInvalidType_whenUpdateCurvePoint_thenReturnErrorMessageAndTradeUpdateForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                .sessionAttr("curvePointDTO", curvePoint1DTO)
                .param("curveId", curvePoint1DTO.getCurveId().toString())
                .param("term", curvePoint1DTO.getTerm().toString())
                .param("value", BigDecimal.valueOf(-100).toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/update"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("The value must be positive");
        verify(curvePointService, times(0)).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
    }

    @Test
    @Tag("DeleteCurvePoint")
    @DisplayName("Given a curvePoint to delete, when deleteCurvePoint request, then redirect to curvePoint list page")
    void givenACurvePointToDelete_whenUpdateCurvePoint_thenRedirectToCurvePointListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).deleteCurvePoint(1);
    }
}
