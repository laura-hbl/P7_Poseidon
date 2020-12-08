package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.util.DTOConverter;
import com.nnk.springboot.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService implements IRatingService {

    private static final Logger LOGGER = LogManager.getLogger(RatingService.class);

    private final RatingRepository ratingRepository;

    private final DTOConverter dtoConverter;

    private final ModelConverter modelConverter;

    @Autowired
    public RatingService(final RatingRepository ratingRepository, final DTOConverter dtoConverter,
                         final ModelConverter modelConverter) {
        this.ratingRepository = ratingRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    public RatingDTO addRating(final RatingDTO ratingDTO) {
        LOGGER.debug("Inside RatingService.updateRating");

        Rating rating = modelConverter.toRating(ratingDTO);
        Rating ratingUpdated = ratingRepository.save(rating);

        return dtoConverter.toRatingDTO(ratingUpdated);
    }

    public RatingDTO updateRating(final int ratingId, final RatingDTO ratingDTO) {
        LOGGER.debug("Inside RatingService.updateRating");

        ratingDTO.setId(ratingId);
        Rating rating = modelConverter.toRating(ratingDTO);
        Rating ratingUpdated = ratingRepository.save(rating);

        return dtoConverter.toRatingDTO(ratingUpdated);
    }

    public void deleteRating(final int ratingId) {
        LOGGER.debug("Inside RatingService.deleteRating");

        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() ->
                new ResourceNotFoundException("No Rating registered with this id"));

        ratingRepository.delete(rating);
    }

    public RatingDTO getRatingById(final int ratingId) {
        LOGGER.debug("Inside RatingService.getRatingById");

        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() ->
                new ResourceNotFoundException("No rating registered with this id"));

        return dtoConverter.toRatingDTO(rating);
    }

    public List<RatingDTO> getAllRating() {
        LOGGER.debug("Inside RatingService.getAllRating");

        List<Rating> ratings = ratingRepository.findAll();
        List<RatingDTO> ratingList = new ArrayList<>();

        for (Rating rating : ratings) {
            RatingDTO ratingDTO = dtoConverter.toRatingDTO(rating);
            ratingList.add(ratingDTO);
        }

        return ratingList;
    }
}
