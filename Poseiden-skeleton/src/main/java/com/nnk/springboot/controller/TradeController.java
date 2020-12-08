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

        model.addAttribute("trades", tradeService.getAllTrade());

        return "trade/list";
    }

    @GetMapping("/add")
    public String addTrade(final Model model) {

        model.addAttribute("trade", new TradeDTO());

        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final TradeDTO tradeDTO, final BindingResult result) {

        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.addTrade(tradeDTO);

        return "redirect:/trade/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer tradeId, final Model model) {

        TradeDTO trade = tradeService.getTradeById(tradeId);

        model.addAttribute("trade", trade);

        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") final Integer tradeId, @Valid final TradeDTO tradeDTO,
                              final BindingResult result) {

        if (result.hasErrors()) {
            return "trade/update";
        }
        tradeService.updateTrade(tradeId, tradeDTO);

        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") final Integer tradeId) {

        tradeService.deleteTrade(tradeId);

        return "redirect:/trade/list";
    }
}
