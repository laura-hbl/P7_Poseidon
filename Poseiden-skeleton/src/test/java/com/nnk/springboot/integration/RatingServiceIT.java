package com.nnk.springboot.integration;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.RatingService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RatingServiceIT {

    @Autowired
    private RatingService ratingService;

    @Test
    @Tag("AddRating")
    @DisplayName("If rating is not registered, when addRating, then return rating saved")
    public void givenAnUnRegisteredRating_whenAddRating_thenRatingSavedShouldBeReturned() {
        RatingDTO ratingDTO = new RatingDTO("moody3", "standP3", "fitch3", 3);

        RatingDTO ratingSaved = ratingService.addRating(ratingDTO);

        assertNotNull(ratingSaved);
        assertThat(ratingSaved.getMoodysRating()).isEqualTo("moody3");
    }

    @Test
    @Tag("UpdateRating")
    @DisplayName("Given an rating to update, when updateRating, then return rating updated")
    public void givenAnRatingToUpdate_whenUpdateRating_thenRatingUpdatedShouldBeReturned() {
        RatingDTO ratingToUpdate = new RatingDTO("moody1", "standP1", "fitch1", 5);

        RatingDTO ratingUpdated = ratingService.updateRating(1, ratingToUpdate);

        assertNotNull(ratingUpdated);
        assertThat(ratingUpdated.getOrderNumber()).isEqualTo(5);
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateRating")
    @DisplayName("If Rating id cant be found, when updateRating, then throw ResourceNotFoundException")
    public void givenUnFoundRatingId_whenUpdateRating_thenResourceNotFoundExceptionIsThrown() {
        RatingDTO ratingToUpdate = new RatingDTO("moody1", "standP1", "fitch1", 5);

        ratingService.updateRating(6, ratingToUpdate);
    }

    @Test
    @Tag("DeleteRating")
    @DisplayName("Given an rating to delete, when deleteRating, then rating should be delete successfully")
    public void givenAnRatingId_whenDeleteRating_thenRatingShouldBeDeleteSuccessfully() {
        ratingService.deleteRating(2);

        assertThrows(ResourceNotFoundException.class, () -> { ratingService.deleteRating(2);
        });
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteRating")
    @DisplayName("If rating id cant be found, when deleteRating, then throw ResourceNotFoundException")
    public void givenUnFoundRatingId_whenDeleteRating_thenResourceNotFoundExceptionIsThrown() {
        ratingService.deleteRating(6);
    }

    @Test
    @Tag("GetRatingById")
    @DisplayName("Given an rating id, when getRatingById, then expected rating should be returned")
    public void givenAnRatingId_whenGetRatingById_thenExpectedRatingShouldBeReturned() {
        RatingDTO rating = ratingService.getRatingById(2);

        assertNotNull(rating);
        assertThat(rating.getMoodysRating()).isEqualTo("moody2");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetRatingById")
    @DisplayName("If rating id cant be found, when getRatingById, then throw ResourceNotFoundException")
    public void givenUnFoundRatingId_whenGetRatingById_thenResourceNotFoundExceptionIsThrown() {
        ratingService.getRatingById(6);
    }

    @Test
    @Tag("GetAllRating")
    @DisplayName("When getAllRating, then rating list should be returned")
    public void whenGetAllRating_thenRatingListShouldBeReturned() {
        List<RatingDTO> ratings = ratingService.getAllRating();

        assertNotNull(ratings);
        assertThat(ratings.size()).isEqualTo(2);
    }
}
