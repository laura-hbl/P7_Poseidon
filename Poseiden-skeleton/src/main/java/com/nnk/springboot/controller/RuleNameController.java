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

@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    private static final Logger LOGGER = LogManager.getLogger(RuleNameController.class);

    private final IRuleNameService ruleNameService;

    @Autowired
    public RuleNameController(final IRuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @GetMapping("/list")
    public String home(final Model model) {
        LOGGER.debug("GET Request on /ruleName/list");

        model.addAttribute("ruleNames", ruleNameService.getAllRuleName());

        LOGGER.info("GET Request on /ruleName/list - SUCCESS");
        return "ruleName/list";
    }

    @GetMapping("/add")
    public String addRuleForm(final Model model) {
        LOGGER.debug("GET Request on /ruleName/add");

        model.addAttribute("ruleNameDTO", new RuleNameDTO());

        LOGGER.info("GET Request on /ruleName/add - SUCCESS");
        return "ruleName/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final RuleNameDTO ruleNameDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /ruleName/validate");

        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.addRuleName(ruleNameDTO);

        LOGGER.info("POST Request on /ruleName/validate - SUCCESS");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer ruleNameId, final Model model) {
        LOGGER.debug("GET Request on /ruleName/update/{id} with id : {}", ruleNameId);

        RuleNameDTO ruleName = ruleNameService.getRuleNameById(ruleNameId);
        model.addAttribute("ruleNameDTO", ruleName);

        LOGGER.info("GET Request on /ruleName/update/{id} - SUCCESS");
        return "ruleName/update";
    }

    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") final Integer ruleNameId, @Valid final RuleNameDTO ruleNameDTO,
                                 final BindingResult result) {
        LOGGER.debug("POST Request on /ruleName/update/{id} with id : {}", ruleNameId);

        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleNameService.updateRuleName(ruleNameId, ruleNameDTO);

        LOGGER.info("POST Request on /ruleName/update/{id} - SUCCESS");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") final Integer ruleNameId) {
        LOGGER.debug("GET Request on /ruleName/delete/{id} with id : {}", ruleNameId);

        ruleNameService.deleteRuleName(ruleNameId);

        LOGGER.info("GET Request on /ruleName/delete/{id} - SUCCESS");
        return "redirect:/ruleName/list";
    }
}
