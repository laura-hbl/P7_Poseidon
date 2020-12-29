package com.nnk.springboot.controller;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.IBidListService;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Creates REST endpoints for crud operations on BidList data.
 *
 * @author Laura Habdul
 * @see IBidListService
 */
@Controller
@RequestMapping("/bidList")
public class BidListController {

    /**
     * BidListController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(BidListController.class);

    /**
     * IBidListService's implement class reference.
     */
    private final IBidListService bidListService;

    /**
     * Constructor of class BidListController.
     * Initialize personService.
     *
     * @param bidListService IBidListService's implement class reference.
     */
    @Autowired
    public BidListController(final IBidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * Displays bidList list.
     *
     * @param model makes bidList list objects available to bidList/list HTML page.
     * @return The reference to the bidList/list HTML page.
     */
    @GetMapping("/list")
    public String showBidList(final Model model) {
        LOGGER.debug("GET Request on /bidList/list");

        model.addAttribute("bidLists", bidListService.getAllBidList());

        LOGGER.info("GET Request on /bidList/list - SUCCESS");
        return "bidList/list";
    }

    /**
     * Displays form for adding a new bidList.
     *
     * @param model makes a new BidListDTO object available to bidList/add HTML page
     * @return The reference to the bidList/add HTML page
     */
    @GetMapping("/add")
    public String addBidForm(final Model model) {
        LOGGER.debug("GET Request on /bidList/add");

        model.addAttribute("bidListDTO", new BidListDTO());

        LOGGER.info("GET Request on /bidList/add - SUCCESS");
        return "bidList/add";
    }

    /**
     * Adds a new bidList.
     *
     * @param bidListDTO the bidList to be added
     * @param result     holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the bidList/add HTML page if result has errors. Else, redirects to
     * /bidList/list endpoint
     */
    @PostMapping("/validate")
    public String validate(@Valid final BidListDTO bidListDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /bidList/validate");

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "bidList/add";
        }
        bidListService.addBidList(bidListDTO);

        LOGGER.info("POST Request on /bidList/validate - SUCCESS");
        return "redirect:/bidList/list";
    }

    /**
     * Displays a form to update an existing bidList.
     *
     * @param bidListId id of the bidList to be updated
     * @param model     makes the bidList object to be updated available to user/update HTML page
     * @return The reference to the bidList/update HTML page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer bidListId, final Model model) {
        LOGGER.debug("GET Request on /bidList/update/{id} with id : {}", bidListId);

        BidListDTO bidList = bidListService.getBidListById(bidListId);
        model.addAttribute("bidListDTO", bidList);

        LOGGER.info("GET Request on /bidList/update/{id} - SUCCESS");
        return "bidList/update";
    }

    /**
     * Updates a stored bidList.
     *
     * @param bidListId id of the bidList to be updated
     * @param bidListId the bidList to be updated
     * @param result    holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the bidList/update HTML page if result has errors. Else, redirects to
     * /bidList/list endpoint
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") final Integer bidListId, @Valid final BidListDTO bidListDTO,
                            final BindingResult result) {
        LOGGER.debug("POST Request on /bidList/update/{id} with id : {}", bidListId);

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "bidList/update";
        }
        bidListService.updateBidList(bidListId, bidListDTO);

        LOGGER.info("POST Request on /bidList/update/{id} - SUCCESS");
        return "redirect:/bidList/list";
    }

    /**
     * Deletes a stored bidList.
     *
     * @param bidListId id of the bidList to be deleted
     * @return Redirects to /bidList/list endpoint
     */
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer bidListId) {
        LOGGER.debug("GET Request on /bidList/delete/{id} with id : {}", bidListId);

        bidListService.deleteBidList(bidListId);

        LOGGER.info("GET Request on /bidList/delete/{id} - SUCCESS");
        return "redirect:/bidList/list";
    }
}
