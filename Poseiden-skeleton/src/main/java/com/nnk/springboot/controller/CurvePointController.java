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

/**
 * Creates REST endpoints for crud operations on CurvePoint data.
 *
 * @author Laura Habdul
 * @see ICurvePointService
 */
@Controller
@RequestMapping("/curvePoint")
public class CurvePointController {

    /**
     * CurvePointController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(CurvePointController.class);

    /**
     * ICurvePointService's implement class reference.
     */
    private final ICurvePointService curvePointService;

    /**
     * Constructor of class CurvePointController.
     * Initialize curvePointService.
     *
     * @param curvePointService ICurvePointService's implement class reference.
     */
    @Autowired
    public CurvePointController(final ICurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * Displays curvePoint list.
     *
     * @param model makes curvePoint list objects available to curvePoint/list HTML page.
     * @return The reference to the curvePoint/list HTML page.
     */
    @GetMapping("/list")
    public String showCurvePointList(final Model model) {
        LOGGER.debug("GET Request on /curvePoint/list");

        model.addAttribute("curvePoints", curvePointService.getAllCurvePoint());

        LOGGER.info("GET Request on /curvePoint/list - SUCCESS");
        return "curvePoint/list";
    }

    /**
     * Displays form for adding a new curvePoint.
     *
     * @param model makes a new CurvePointDTO object available to curvePoint/add HTML page
     * @return The reference to the curvePoint/add HTML page
     */
    @GetMapping("/add")
    public String addCurvePointForm(final Model model) {
        LOGGER.debug("GET Request on /curvePoint/add");

        model.addAttribute("curvePointDTO", new CurvePointDTO());

        LOGGER.info("GET Request on /curvePoint/add - SUCCESS");
        return "curvePoint/add";
    }

    /**
     * Adds a new curvePoint.
     *
     * @param curvePointDTO the curvePoint to be added
     * @param result        holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the curvePoint/add HTML page if result has errors. Else, redirects to
     * /curvePoint/list endpoint
     */
    @PostMapping("/validate")
    public String validate(@Valid final CurvePointDTO curvePointDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /curvePoint/validate");

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "curvePoint/add";
        }
        curvePointService.addCurvePoint(curvePointDTO);

        LOGGER.info("POST Request on /curvePoint/validate - SUCCESS");
        return "redirect:/curvePoint/list";
    }

    /**
     * Displays a form to update an existing curvePoint.
     *
     * @param curvePointId id of the curvePoint to be updated
     * @param model        makes the curvePoint object to be updated available to user/update HTML page
     * @return The reference to the curvePoint/update HTML page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer curvePointId, final Model model) {
        LOGGER.debug("GET Request on /curvePoint/update/{id} with id : {}", curvePointId);

        CurvePointDTO curvePoint = curvePointService.getCurvePointById(curvePointId);
        model.addAttribute("curvePointDTO", curvePoint);

        LOGGER.info("GET Request on /curvePoint/update/{id} - SUCCESS");
        return "curvePoint/update";
    }

    /**
     * Updates a stored curvePoint.
     *
     * @param curvePointId id of the curvePoint to be updated
     * @param curvePointId the curvePoint to be updated
     * @param result       holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the curvePoint/update HTML page if result has errors. Else, redirects to
     * /curvePoint/list endpoint
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") final Integer curvePointId, @Valid final CurvePointDTO curvePointDTO,
                            final BindingResult result) {
        LOGGER.debug("POST Request on /curvePoint/update/{id} with id : {}", curvePointId);

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(curvePointId, curvePointDTO);

        LOGGER.info("POST Request on /curvePoint/update/{id} - SUCCESS");
        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes a stored curvePoint.
     *
     * @param curvePointId id of the curvePoint to be deleted
     * @return Redirects to /curvePoint/list endpoint
     */
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer curvePointId) {
        LOGGER.debug("GET Request on /curvePoint/delete/{id} with id : {}", curvePointId);

        curvePointService.deleteCurvePoint(curvePointId);

        LOGGER.info("GET Request on /curvePoint/delete/{id} - SUCCESS");
        return "redirect:/curvePoint/list";
    }
}
