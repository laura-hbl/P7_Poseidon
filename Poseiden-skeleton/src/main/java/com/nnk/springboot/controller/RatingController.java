package com.nnk.springboot.controller;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.IRatingService;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Creates REST endpoints for crud operations on Rating data.
 *
 * @author Laura Habdul
 * @see IRatingService
 */
@Controller
@RequestMapping("/rating")
public class RatingController {

    /**
     * RatingController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(RatingController.class);

    /**
     * IRatingService's implement class reference.
     */
    private final IRatingService ratingService;

    /**
     * Constructor of class RatingController.
     * Initialize ratingService.
     *
     * @param ratingService IRatingService's implement class reference.
     */
    @Autowired
    public RatingController(final IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Displays rating list.
     *
     * @param model makes rating list objects available to rating/list HTML page.
     * @return The reference to the rating/list HTML page.
     */
    @GetMapping("/list")
    public String showRatingList(final Model model) {
        LOGGER.debug("GET Request on /rating/list");

        model.addAttribute("ratings", ratingService.getAllRating());

        LOGGER.info("GET Request on /rating/list - SUCCESS");
        return "rating/list";
    }

    /**
     * Displays form for adding a new rating.
     *
     * @param model makes a new RatingDTO object available to rating/add HTML page
     * @return The reference to the rating/add HTML page
     */
    @GetMapping("/add")
    public String addRatingForm(final Model model) {
        LOGGER.debug("GET Request on /rating/add");

        model.addAttribute("ratingDTO", new RatingDTO());

        LOGGER.info("GET Request on /rating/add - SUCCESS");
        return "rating/add";
    }

    /**
     * Adds a new rating.
     *
     * @param ratingDTO the rating to be added
     * @param result    holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the rating/add HTML page if result has errors. Else, redirects to /rating/list endpoint
     */
    @PostMapping("/validate")
    public String validate(@Valid final RatingDTO ratingDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /rating/validate");

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "rating/add";
        }
        ratingService.addRating(ratingDTO);

        LOGGER.info("POST Request on /rating/validate - SUCCESS");
        return "redirect:/rating/list";
    }

    /**
     * Displays a form to update an existing rating.
     *
     * @param ratingId id of the rating to be updated
     * @param model    makes the rating object to be updated available to user/update HTML page
     * @return The reference to the rating/update HTML page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer ratingId, final Model model) {
        LOGGER.debug("GET Request on /rating/update/{id} with id : {}", ratingId);

        RatingDTO rating = ratingService.getRatingById(ratingId);
        model.addAttribute("ratingDTO", rating);

        LOGGER.info("GET Request on /rating/update/{id} - SUCCESS");
        return "rating/update";
    }

    /**
     * Updates a stored rating.
     *
     * @param ratingId id of the rating to be updated
     * @param ratingId the rating to be updated
     * @param result   holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the rating/update HTML page if result has errors. Else, redirects to
     * /rating/list endpoint
     */
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") final Integer ratingId, @Valid final RatingDTO ratingDTO,
                               final BindingResult result) {
        LOGGER.debug("POST Request on /rating/update/{id} with id : {}", ratingId);

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "rating/update";
        }
        ratingService.updateRating(ratingId, ratingDTO);

        LOGGER.info("POST Request on /rating/update/{id} - SUCCESS");
        return "redirect:/rating/list";
    }

    /**
     * Deletes a stored rating.
     *
     * @param ratingId id of the rating to be deleted
     * @return Redirects to /rating/list endpoint
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") final Integer ratingId) {
        LOGGER.debug("GET Request on /rating/delete/{id} with id : {}", ratingId);

        ratingService.deleteRating(ratingId);

        LOGGER.info("GET Request on /rating/delete/{id} - SUCCESS");
        return "redirect:/rating/list";
    }
}
