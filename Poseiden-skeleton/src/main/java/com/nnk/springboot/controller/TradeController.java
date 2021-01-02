package com.nnk.springboot.controller;

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.ITradeService;
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
 * Creates REST endpoints for crud operations on Trade data.
 *
 * @author Laura Habdul
 * @see ITradeService
 */
@Controller
@RequestMapping("/trade")
public class TradeController {

    /**
     * TradeController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(TradeController.class);

    /**
     * ITradeService's implement class reference.
     */
    private final ITradeService tradeService;

    /**
     * Constructor of class TradeController.
     * Initialize tradeService.
     *
     * @param tradeService ITradeService's implement class reference
     */
    @Autowired
    public TradeController(final ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Displays trade list.
     *
     * @param model makes trade list objects available to trade/list HTML page
     * @return The reference to the trade/list HTML page.
     */
    @GetMapping("/list")
    public String showTradeList(final Model model) {
        LOGGER.debug("GET Request on /trade/list");

        model.addAttribute("trades", tradeService.getAllTrade());

        LOGGER.info("GET Request on /trade/list - SUCCESS");
        return "trade/list";
    }

    /**
     * Displays form for adding a new trade.
     *
     * @param model makes a new TradeDTO object available to trade/add HTML page
     * @return The reference to the trade/add HTML page
     */
    @GetMapping("/add")
    public String addTradeForm(final Model model) {
        LOGGER.debug("GET Request on /trade/add");

        model.addAttribute("tradeDTO", new TradeDTO());

        LOGGER.info("GET Request on /trade/add - SUCCESS");
        return "trade/add";
    }

    /**
     * Adds a new trade.
     *
     * @param tradeDTO the trade to be added
     * @param result   holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the trade/add HTML page if result has errors. Else, redirects to /trade/list endpoint
     */
    @PostMapping("/validate")
    public String validate(@Valid final TradeDTO tradeDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /trade/validate");

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "trade/add";
        }
        tradeService.addTrade(tradeDTO);

        LOGGER.info("POST Request on /trade/validate - SUCCESS");
        return "redirect:/trade/list";
    }

    /**
     * Displays a form to update an existing trade.
     *
     * @param tradeId id of the trade to be updated
     * @param model   makes the trade object to be updated available to user/update HTML page
     * @return The reference to the trade/update HTML page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer tradeId, final Model model) {
        LOGGER.debug("GET Request on /trade/update/{id} with id : {}", tradeId);

        TradeDTO trade = tradeService.getTradeById(tradeId);
        model.addAttribute("tradeDTO", trade);

        LOGGER.info("GET Request on /trade/update/{id} - SUCCESS");
        return "trade/update";
    }

    /**
     * Updates a saved trade.
     *
     * @param tradeId  id of the trade to be updated
     * @param tradeDTO the trade to be updated
     * @param result   holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the trade/update HTML page if result has errors. Else, redirects to /trade/list endpoint
     */
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") final Integer tradeId, @Valid final TradeDTO tradeDTO,
                              final BindingResult result) {
        LOGGER.debug("POST Request on /trade/update/{id} with id : {}", tradeId);

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "trade/update";
        }
        tradeService.updateTrade(tradeId, tradeDTO);

        LOGGER.info("POST Request on /trade/update/{id} - SUCCESS");
        return "redirect:/trade/list";
    }

    /**
     * Deletes a saved trade.
     *
     * @param tradeId id of the trade to be deleted
     * @return The redirects to /trade/list endpoint
     */
    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") final Integer tradeId) {
        LOGGER.debug("GET Request on /trade/delete/{id} with id : {}", tradeId);

        tradeService.deleteTrade(tradeId);

        LOGGER.info("GET Request on /trade/delete/{id} - SUCCESS");
        return "redirect:/trade/list";
    }
}
