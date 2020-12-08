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

        model.addAttribute("curvePoints", curvePointService.getAllCurvePoint());

        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addCurvePointForm(final Model model) {

        model.addAttribute("curvePoint", new CurvePointDTO());

        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final CurvePointDTO curvePointDTO, final BindingResult result) {

        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        curvePointService.addCurvePoint(curvePointDTO);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer curvePointId, final Model model) {

        CurvePointDTO curvePoint = curvePointService.getCurvePointById(curvePointId);

        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") final Integer curvePointId, @Valid final CurvePointDTO curvePointDTO,
                            final BindingResult result) {

        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(curvePointId, curvePointDTO);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer curvePointId) {

        curvePointService.deleteCurvePoint(curvePointId);

        return "redirect:/curvePoint/list";
    }
}
