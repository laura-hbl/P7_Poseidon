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

        model.addAttribute("ratings", ratingService.getAllRating());

        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(final Model model) {

        model.addAttribute("rating", new RatingDTO());

        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final RatingDTO ratingDTO, final BindingResult result) {

        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.addRating(ratingDTO);

        return "redirect:/rating/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer ratingId, final Model model) {

        RatingDTO rating = ratingService.getRatingById(ratingId);

        model.addAttribute("rating", rating);

        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") final Integer ratingId, @Valid final RatingDTO ratingDTO,
                            final BindingResult result) {

        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingService.updateRating(ratingId, ratingDTO);

        return "redirect:/rating/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") final Integer ratingId) {

        ratingService.deleteRating(ratingId);

        return "redirect:/rating/list";
    }
}
