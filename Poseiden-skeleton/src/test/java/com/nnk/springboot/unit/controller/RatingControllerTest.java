package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controller.RatingController;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.security.jwt.JwtUtils;
import com.nnk.springboot.service.RatingService;
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

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @MockBean
    private RatingService ratingService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private RatingDTO rating1DTO;

    private RatingDTO rating2DTO;

    @BeforeEach
    public void setUp() {
        rating1DTO = new RatingDTO(1, "moody", "standP", "fitch", 3);
        rating2DTO = new RatingDTO(2, "moody", "standP", "fitch", 4);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("ShowRatingList")
    @DisplayName("When showRatingList request, then display rating list")
    public void whenShowRatingListRequest_thenDisplayRatingList() throws Exception {
        when(ratingService.getAllRating()).thenReturn(Arrays.asList(rating1DTO, rating2DTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk());

        verify(ratingService).getAllRating();
    }

    @Test
    @Tag("AddRatingForm")
    @DisplayName("When addRatingForm request, then display Rating add form")
    public void whenAddRatingFormRequest_thenDisplayRatingAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(model().attributeExists("ratingDTO"))
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given a valid Rating to add, when validate request, then redirect to rating list page")
    void givenAValidRatingToAdd_whenValidate_thenRedirectToRatingListPage() throws Exception {
        when(ratingService.addRating(any(RatingDTO.class))).thenReturn(any(RatingDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                .sessionAttr("RatingDTO", rating1DTO)
                .param("moodysRating", rating1DTO.getMoodysRating())
                .param("standPoorRating", rating1DTO.getStandPoorRating())
                .param("fitchRating", rating1DTO.getFitchRating())
                .param("orderNumber", rating1DTO.getOrderNumber().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).addRating(any(RatingDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given negative order number, when validate request, then return error message and rating add form")
    void givenNegativeOrderNumber_whenValidate_thenReturnErrorMessageAndRatingAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                .sessionAttr("ratingDTO", rating1DTO)
                .param("moodysRating", rating1DTO.getMoodysRating())
                .param("standPoorRating", rating1DTO.getStandPoorRating())
                .param("fitchRating", rating1DTO.getFitchRating())
                .param("orderNumber", Integer.valueOf(-10).toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("rating/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("The value must be positive");
        verify(ratingService, times(0)).addRating(any(RatingDTO.class));
    }

    @Test
    @Tag("ShowUpdateForm")
    @DisplayName("When showUpdateForm request, then display Rating update form")
    public void whenShowUpdateFormRequest_thenDisplayratingUpdateForm() throws Exception {
        when(ratingService.getRatingById(1)).thenReturn(rating1DTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/1"))
                .andExpect(model().attributeExists("ratingDTO"))
                .andExpect(view().name("rating/update"))
                .andExpect(status().isOk());

        verify(ratingService).getRatingById(1);
    }

    @Test
    @Tag("UpdateRating")
    @DisplayName("Given a valid rating to update, when updateRating request, then redirect to rating list page")
    void givenAValidRatingToUpdate_whenUpdateRating_thenRedirectToRatingListPage() throws Exception {
        RatingDTO ratingUpdateDTO = new RatingDTO( 1, "moody", "standP",
                "fitch", 6);
        when(ratingService.updateRating(1, rating1DTO)).thenReturn(ratingUpdateDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
                .sessionAttr("RatingDTO", rating1DTO)
                .param("moodysRating", rating1DTO.getMoodysRating())
                .param("standPoorRating", rating1DTO.getStandPoorRating())
                .param("fitchRating", rating1DTO.getFitchRating())
                .param("orderNumber", rating1DTO.getOrderNumber().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).updateRating(anyInt(), any(RatingDTO.class));
    }

    @Test
    @Tag("DeleteRating")
    @DisplayName("Given a rating to delete, when deleteRating request, then redirect to rating list page")
    void givenARatingToDelete_whenUpdateRating_thenRedirectToRatingListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).deleteRating(1);
    }
}
