package com.nnk.springboot.service;

import com.nnk.springboot.dto.RatingDTO;

import java.util.List;

/**
 * RatingService interface.
 *
 * @author Laura Habdul
 */
public interface IRatingService {

    /**
     * Adds a new rating in database.
     *
     * @param rating the rating to be added
     * @return The rating saved converted to a RatingDTO object
     */
    RatingDTO addRating(final RatingDTO rating);

    /**
     * Updates an rating based on the given id.
     *
     * @param ratingId id of the rating to be updated
     * @param rating   the rating to be updated
     * @return The rating updated converted to a RatingDTO object
     */
    RatingDTO updateRating(final int ratingId, final RatingDTO rating);

    /**
     * Deletes an rating based on the given id.
     *
     * @param ratingId id of the rating to be deleted
     */
    void deleteRating(final int ratingId);

    /**
     * Retrieves an rating based on the given id.
     *
     * @param ratingId id of the rating to be found
     * @return The rating retrieved converted to a RatingDTO object
     */
    RatingDTO getRatingById(final int ratingId);

    /**
     * Retrieves the rating list.
     *
     * @return The rating list retrieved where each Rating is converted to a RatingDTO object
     */
    List<RatingDTO> getAllRating();
}
