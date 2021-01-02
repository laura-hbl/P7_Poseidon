package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.service.RatingService;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    private static Rating rating1;

    private static Rating rating2;

    private static RatingDTO rating1DTO;

    private static RatingDTO rating2DTO;

    private static List<RatingDTO> ratingListDTO;

    @Before
    public void setUp() {
        rating1DTO = new RatingDTO(1, "moody", "standP", "fitch", 3);
        rating2DTO = new RatingDTO(2, "moody", "standP", "fitch", 4);
        rating1 = new Rating(1, "moody", "standP", "fitch", 3);
        rating2 = new Rating(2, "moody", "standP", "fitch", 4);
        ratingListDTO = Arrays.asList(rating1DTO, rating2DTO);
    }

    @Test
    @Tag("AddRating")
    @DisplayName("Given a rating to save, when addRating, then rating should be saved correctly")
    public void givenARating_whenAddRating_thenRatingShouldBeSavedCorrectly() {
        Rating ratingToAdd = new Rating("moody", "standP", "fitch", 3);
        RatingDTO ratingToAddDTO = new RatingDTO("moody", "standP", "fitch", 3);
        when(modelConverter.toRating(any(RatingDTO.class))).thenReturn(ratingToAdd);
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating1);
        when(dtoConverter.toRatingDTO(any(Rating.class))).thenReturn(rating1DTO);

        RatingDTO ratingSaved = ratingService.addRating(ratingToAddDTO);

        assertThat(ratingSaved).isEqualToComparingFieldByField(rating1DTO);
        InOrder inOrder = inOrder(ratingRepository, dtoConverter, modelConverter);
        inOrder.verify(modelConverter).toRating(any(RatingDTO.class));
        inOrder.verify(ratingRepository).save(any(Rating.class));
        inOrder.verify(dtoConverter).toRatingDTO(any(Rating.class));
    }

    @Test
    @Tag("UpdateRating")
    @DisplayName("Given a registered rating, when updateRating, then rating should be updated correctly")
    public void givenARatingToUpdate_whenUpdateRating_thenRatingShouldBeUpdateCorrectly() {
        RatingDTO rating1DTOUpdated = new RatingDTO(1, "moody", "standP", "fitch",
                7);
        Rating rating1Updated = new Rating(1, "moody", "standP", "fitch",
                7);
        when(ratingRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(rating1));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating1Updated);
        when(dtoConverter.toRatingDTO(any(Rating.class))).thenReturn(rating1DTOUpdated);

        RatingDTO result = ratingService.updateRating(1, new RatingDTO("moody", "standP",
                "fitch", 7));

        assertThat(result).isEqualTo(rating1DTOUpdated);
        InOrder inOrder = inOrder(ratingRepository, dtoConverter);
        inOrder.verify(ratingRepository).findById(anyInt());
        inOrder.verify(ratingRepository).save(any(Rating.class));
        inOrder.verify(dtoConverter).toRatingDTO(any(Rating.class));
    }

    @Test
    @Tag("DeleteRating")
    @DisplayName("Given a rating Id, when deleteRating, then delete process should be done in correct order")
    public void givenARatingId_whenDeleteRating_thenDeletingShouldBeDoneInCorrectOrder() {
        when(ratingRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(rating1));

        ratingService.deleteRating(anyInt());

        InOrder inOrder = inOrder(ratingRepository);
        inOrder.verify(ratingRepository).findById(anyInt());
        inOrder.verify(ratingRepository).deleteById(anyInt());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteRating")
    @DisplayName("If rating can't be found, when deleteRating, then throw ResourceNotFoundException")
    public void givenUnFoundRating_whenDeleteRating_thenResourceNotFoundExceptionIsThrown() {
        when(ratingRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        ratingService.deleteRating(anyInt());
    }

    @Test
    @Tag("GetRatingById")
    @DisplayName("Given a rating id, when getRatingById, then expected rating should be returned correctly")
    public void givenARatingId_whenGetRatingById_thenExpectedRatingShouldBeReturnCorrectly() {
        when(ratingRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(rating1));
        when(dtoConverter.toRatingDTO(any(Rating.class))).thenReturn(rating1DTO);

        RatingDTO ratingFound = ratingService.getRatingById(1);

        assertThat(ratingFound).isEqualToComparingFieldByField(rating1DTO);
        InOrder inOrder = inOrder(ratingRepository, dtoConverter);
        inOrder.verify(ratingRepository).findById(anyInt());
        inOrder.verify(dtoConverter).toRatingDTO(any(Rating.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetRatingById")
    @DisplayName("If rating can't be found, when getRatingById, then throw ResourceNotFoundException")
    public void givenUnFoundRating_whenGetRatingById_thenResourceNotFoundExceptionIsThrown() {
        when(ratingRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        ratingService.getRatingById(1);
    }

    @Test
    @Tag("GetAllRating")
    @DisplayName("Given an rating list, when getAllRating, then result should match expected rating list")
    public void givenARatingList_whenGetAllRating_thenReturnExpectedRatingList() {
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        when(dtoConverter.toRatingDTO(rating1)).thenReturn(rating1DTO);
        when(dtoConverter.toRatingDTO(rating2)).thenReturn(rating2DTO);

        List<RatingDTO> result = ratingService.getAllRating();

        assertThat(result).isEqualTo(ratingListDTO);
        InOrder inOrder = inOrder(ratingRepository, dtoConverter);
        inOrder.verify(ratingRepository).findAll();
        inOrder.verify(dtoConverter).toRatingDTO(rating1);
        inOrder.verify(dtoConverter).toRatingDTO(rating2);
    }
}
