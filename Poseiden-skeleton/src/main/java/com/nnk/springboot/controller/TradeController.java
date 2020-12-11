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

@Controller
@RequestMapping("/trade")
public class TradeController {

    private static final Logger LOGGER = LogManager.getLogger(TradeController.class);

    private final ITradeService tradeService;

    @Autowired
    public TradeController(final ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/list")
    public String home(final Model model) {
        LOGGER.debug("GET Request on /trade/list");

        model.addAttribute("trades", tradeService.getAllTrade());

        LOGGER.info("GET Request on /trade/list - SUCCESS");
        return "trade/list";
    }

    @GetMapping("/add")
    public String addTrade(final Model model) {
        LOGGER.debug("GET Request on /trade/add");

        model.addAttribute("trade", new TradeDTO());

        LOGGER.info("GET Request on /trade/add - SUCCESS");
        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final TradeDTO tradeDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /trade/validate");

        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.addTrade(tradeDTO);

        LOGGER.info("POST Request on /trade/validate - SUCCESS");
        return "redirect:/trade/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer tradeId, final Model model) {
        LOGGER.debug("GET Request on /trade/update/{id} with id : {}", tradeId);

        TradeDTO trade = tradeService.getTradeById(tradeId);
        model.addAttribute("trade", trade);

        LOGGER.info("GET Request on /trade/update/{id} - SUCCESS");
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") final Integer tradeId, @Valid final TradeDTO tradeDTO,
                              final BindingResult result) {
        LOGGER.debug("POST Request on /trade/update/{id} with id : {}", tradeId);

        if (result.hasErrors()) {
            return "trade/update";
        }
        tradeService.updateTrade(tradeId, tradeDTO);

        LOGGER.info("POST Request on /trade/update/{id} - SUCCESS");
        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") final Integer tradeId) {
        LOGGER.debug("GET Request on /trade/delete/{id} with id : {}", tradeId);

        tradeService.deleteTrade(tradeId);

        LOGGER.info("GET Request on /trade/delete/{id} - SUCCESS");
        return "redirect:/trade/list";
    }
}
