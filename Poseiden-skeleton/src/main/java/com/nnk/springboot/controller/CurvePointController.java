package com.nnk.springboot.controller;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.ICurvePointService;
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
@RequestMapping("/curvePoint")
public class CurvePointController {

    private static final Logger LOGGER = LogManager.getLogger(CurvePointController.class);

    private final ICurvePointService curvePointService;

    @Autowired
    public CurvePointController(final ICurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @GetMapping("/list")
    public String home(final Model model) {
        LOGGER.debug("GET Request on /curvePoint/list");

        model.addAttribute("curvePoints", curvePointService.getAllCurvePoint());

        LOGGER.info("GET Request on /curvePoint/list - SUCCESS");
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addCurvePointForm(final Model model) {
        LOGGER.debug("GET Request on /curvePoint/add");

        model.addAttribute("curvePoint", new CurvePointDTO());

        LOGGER.info("GET Request on /curvePoint/add - SUCCESS");
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final CurvePointDTO curvePointDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /curvePoint/validate");

        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        curvePointService.addCurvePoint(curvePointDTO);

        LOGGER.info("POST Request on /curvePoint/validate - SUCCESS");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer curvePointId, final Model model) {
        LOGGER.debug("GET Request on /curvePoint/update/{id} with id : {}", curvePointId);

        CurvePointDTO curvePoint = curvePointService.getCurvePointById(curvePointId);
        model.addAttribute("curvePoint", curvePoint);

        LOGGER.info("GET Request on /curvePoint/update/{id} - SUCCESS");
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") final Integer curvePointId, @Valid final CurvePointDTO curvePointDTO,
                            final BindingResult result) {
        LOGGER.debug("POST Request on /curvePoint/update/{id} with id : {}", curvePointId);

        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(curvePointId, curvePointDTO);

        LOGGER.info("POST Request on /curvePoint/update/{id} - SUCCESS");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer curvePointId) {
        LOGGER.debug("GET Request on /curvePoint/delete/{id} with id : {}", curvePointId);

        curvePointService.deleteCurvePoint(curvePointId);

        LOGGER.info("GET Request on /curvePoint/delete/{id} - SUCCESS");
        return "redirect:/curvePoint/list";
    }
}
