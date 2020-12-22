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

@Controller
@RequestMapping("/rating")
public class RatingController {

    private static final Logger LOGGER = LogManager.getLogger(RatingController.class);

    private final IRatingService ratingService;

    @Autowired
    public RatingController(final IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/list")
    public String home(final Model model) {
        LOGGER.debug("GET Request on /rating/list");

        model.addAttribute("ratings", ratingService.getAllRating());

        LOGGER.info("GET Request on /rating/list - SUCCESS");
        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(final Model model) {
        LOGGER.debug("GET Request on /rating/add");

        model.addAttribute("ratingDTO", new RatingDTO());

        LOGGER.info("GET Request on /rating/add - SUCCESS");
        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final RatingDTO ratingDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /rating/validate");

        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.addRating(ratingDTO);

        LOGGER.info("POST Request on /rating/validate - SUCCESS");
        return "redirect:/rating/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer ratingId, final Model model) {
        LOGGER.debug("GET Request on /rating/update/{id} with id : {}", ratingId);

        RatingDTO rating = ratingService.getRatingById(ratingId);
        model.addAttribute("ratingDTO", rating);

        LOGGER.info("GET Request on /rating/update/{id} - SUCCESS");
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") final Integer ratingId, @Valid final RatingDTO ratingDTO,
                            final BindingResult result) {
        LOGGER.debug("POST Request on /rating/update/{id} with id : {}", ratingId);

        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingService.updateRating(ratingId, ratingDTO);

        LOGGER.info("POST Request on /rating/update/{id} - SUCCESS");
        return "redirect:/rating/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") final Integer ratingId) {
        LOGGER.debug("GET Request on /rating/delete/{id} with id : {}", ratingId);

        ratingService.deleteRating(ratingId);

        LOGGER.info("GET Request on /rating/delete/{id} - SUCCESS");
        return "redirect:/rating/list";
    }
}
