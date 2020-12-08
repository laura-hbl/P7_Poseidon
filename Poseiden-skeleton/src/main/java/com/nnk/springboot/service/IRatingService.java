package com.nnk.springboot.service;

import com.nnk.springboot.dto.RatingDTO;

import java.util.List;

public interface IRatingService {

    RatingDTO addRating(final RatingDTO rating);

    RatingDTO updateRating(final int ratingId, final RatingDTO rating);

    void deleteRating(final int ratingId);

    RatingDTO getRatingById(final int ratingId);

    List<RatingDTO> getAllRating();
}
