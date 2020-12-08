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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/bidList")
public class BidListController {

    private static final Logger LOGGER = LogManager.getLogger(BidListController.class);

    private final IBidListService bidListService;

    @Autowired
    public BidListController(final IBidListService bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping("/list")
    public String home(final Model model) {

        model.addAttribute("bidLists", bidListService.getAllBidList());

        return "bidList/list";
    }

    @GetMapping("/add")
    public String addBidForm(final Model model) {

        model.addAttribute("bidList", new BidListDTO());

        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final BidListDTO bidListDTO, final BindingResult result) {

        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.addBidList(bidListDTO);

        return "redirect:/bidList/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer bidListId, final Model model) {

        BidListDTO bidList = bidListService.getBidListById(bidListId);

        model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") final Integer bidListId, @Valid final BidListDTO bidListDTO,
                            final BindingResult result) {

        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidListService.updateBidList(bidListId, bidListDTO);

        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer bidListId) {

        bidListService.deleteBidList(bidListId);

        return "redirect:/bidList/list";
    }
}
