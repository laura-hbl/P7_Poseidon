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

/**
 * Contains methods that allow interaction between Rating business logic and Rating repository.
 *
 * @author Laura Habdul
 */
@Service
public class RatingService implements IRatingService {

    /**
     * RatingService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(RatingService.class);

    /**
     * RatingRepository instance.
     */
    private final RatingRepository ratingRepository;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * Constructor of class RatingService.
     * Initialize ratingRepository, dtoConverter and modelConverter.
     *
     * @param ratingRepository RatingRepository instance
     * @param dtoConverter     DTOConverter instance
     * @param modelConverter   ModelConverter instance
     */
    @Autowired
    public RatingService(final RatingRepository ratingRepository, final DTOConverter dtoConverter,
                         final ModelConverter modelConverter) {
        this.ratingRepository = ratingRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    /**
     * Converts the ratingDTO object to add to a Rating Model object, saved it to database by calling
     * RatingRepository's save method. Then, converts the saved rating to a RatingDTO object.
     *
     * @param ratingDTO the rating to be added
     * @return The rating saved converted to a RatingDTO object
     */
    public RatingDTO addRating(final RatingDTO ratingDTO) {
        LOGGER.debug("Inside RatingService.addRating");

        Rating rating = modelConverter.toRating(ratingDTO);
        Rating ratingSaved = ratingRepository.save(rating);

        return dtoConverter.toRatingDTO(ratingSaved);
    }

    /**
     * Checks if the given rating to update is registered by calling RatingRepository's findById method, if so
     * rating found is updated, then saved to database by calling RatingRepository's save method and converted
     * to a RatingDTO object. Else, ResourceNotFoundException is thrown.
     *
     * @param ratingId  id of the rating to be updated
     * @param ratingDTO the rating to be updated
     * @return The rating updated converted to a RatingDTO object
     */
    public RatingDTO updateRating(final int ratingId, final RatingDTO ratingDTO) {
        LOGGER.debug("Inside RatingService.updateRating");

        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() ->
                new ResourceNotFoundException("No rating registered with this id"));

        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setStandPoorRating(ratingDTO.getStandPoorRating());
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setOrderNumber(ratingDTO.getOrderNumber());

        Rating ratingUpdated = ratingRepository.save(rating);

        return dtoConverter.toRatingDTO(ratingUpdated);
    }

    /**
     * Checks if the given rating to delete is registered by calling RatingRepository's findById method, if so
     * rating found is deleted by calling RatingRepository's delete method. Else, ResourceNotFoundException is
     * thrown.
     *
     * @param ratingId id of the rating to be deleted
     */
    public void deleteRating(final int ratingId) {
        LOGGER.debug("Inside RatingService.deleteRating");

        ratingRepository.findById(ratingId).orElseThrow(() ->
                new ResourceNotFoundException("No rating registered with this id"));

        ratingRepository.deleteById(ratingId);
    }

    /**
     * Calls RatingRepository's findById method to retrieves the rating with the given id and checks if rating
     * exists in database, if so converts the found Rating to RatingDTO object. Else, ResourceNotFoundException
     * is thrown.
     *
     * @param ratingId id of the rating to be found
     * @return The rating found converted to an RatingDTO object
     */
    public RatingDTO getRatingById(final int ratingId) {
        LOGGER.debug("Inside RatingService.getRatingById");

        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() ->
                new ResourceNotFoundException("No rating registered with this id"));

        return dtoConverter.toRatingDTO(rating);
    }

    /**
     * Retrieves all ratings by calling RatingRepository's findAll() method and each rating from the list is
     * converted to an RatingDTO object and added to an ArrayList.
     *
     * @return The rating list
     */
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
