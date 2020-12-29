package com.nnk.springboot.controller;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.IRuleNameService;
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
 * Creates REST endpoints for crud operations on RuleName data.
 *
 * @author Laura Habdul
 * @see IRuleNameService
 */
@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    /**
     * RuleNameController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(RuleNameController.class);

    /**
     * IRuleNameService's implement class reference.
     */
    private final IRuleNameService ruleNameService;

    /**
     * Constructor of class RuleNameController.
     * Initialize ruleNameService.
     *
     * @param ruleNameService IRuleNameService's implement class reference.
     */
    @Autowired
    public RuleNameController(final IRuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * Displays ruleName list.
     *
     * @param model makes ruleName list objects available to ruleName/list HTML page.
     * @return The reference to the ruleName/list HTML page.
     */
    @GetMapping("/list")
    public String showRuleNameList(final Model model) {
        LOGGER.debug("GET Request on /ruleName/list");

        model.addAttribute("ruleNames", ruleNameService.getAllRuleName());

        LOGGER.info("GET Request on /ruleName/list - SUCCESS");
        return "ruleName/list";
    }

    /**
     * Displays form for adding a new ruleName.
     *
     * @param model makes a new RuleNameDTO object available to ruleName/add HTML page
     * @return The reference to the ruleName/add HTML page
     */
    @GetMapping("/add")
    public String addRuleForm(final Model model) {
        LOGGER.debug("GET Request on /ruleName/add");

        model.addAttribute("ruleNameDTO", new RuleNameDTO());

        LOGGER.info("GET Request on /ruleName/add - SUCCESS");
        return "ruleName/add";
    }

    /**
     * Adds a new ruleName.
     *
     * @param ruleNameDTO the ruleName to be added
     * @param result      holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the ruleName/add HTML page if result has errors. Else, redirects to
     * /ruleName/list endpoint
     */
    @PostMapping("/validate")
    public String validate(@Valid final RuleNameDTO ruleNameDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /ruleName/validate");

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "ruleName/add";
        }
        ruleNameService.addRuleName(ruleNameDTO);

        LOGGER.info("POST Request on /ruleName/validate - SUCCESS");
        return "redirect:/ruleName/list";
    }


    /**
     * Displays a form to update an existing ruleName.
     *
     * @param ruleNameId id of the ruleName to be updated
     * @param model      makes the ruleName object to be updated available to user/update HTML page
     * @return The reference to the ruleName/update HTML page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer ruleNameId, final Model model) {
        LOGGER.debug("GET Request on /ruleName/update/{id} with id : {}", ruleNameId);

        RuleNameDTO ruleName = ruleNameService.getRuleNameById(ruleNameId);
        model.addAttribute("ruleNameDTO", ruleName);

        LOGGER.info("GET Request on /ruleName/update/{id} - SUCCESS");
        return "ruleName/update";
    }

    /**
     * Updates a stored ruleName.
     *
     * @param ruleNameId id of the ruleName to be updated
     * @param ruleNameId the ruleName to be updated
     * @param result     holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the ruleName/update HTML page if result has errors. Else, redirects to
     * /ruleName/list endpoint
     */
    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") final Integer ruleNameId, @Valid final RuleNameDTO ruleNameDTO,
                                 final BindingResult result) {
        LOGGER.debug("POST Request on /ruleName/update/{id} with id : {}", ruleNameId);

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "ruleName/update";
        }
        ruleNameService.updateRuleName(ruleNameId, ruleNameDTO);

        LOGGER.info("POST Request on /ruleName/update/{id} - SUCCESS");
        return "redirect:/ruleName/list";
    }

    /**
     * Deletes a stored ruleName.
     *
     * @param ruleNameId id of the ruleName to be deleted
     * @return Redirects to /ruleName/list endpoint
     */
    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") final Integer ruleNameId) {
        LOGGER.debug("GET Request on /ruleName/delete/{id} with id : {}", ruleNameId);

        ruleNameService.deleteRuleName(ruleNameId);

        LOGGER.info("GET Request on /ruleName/delete/{id} - SUCCESS");
        return "redirect:/ruleName/list";
    }
}
