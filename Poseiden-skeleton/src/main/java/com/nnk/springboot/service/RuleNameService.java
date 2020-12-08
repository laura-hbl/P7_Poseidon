package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.RuleNameRepository;
import com.nnk.springboot.util.DTOConverter;
import com.nnk.springboot.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleNameService implements IRuleNameService {

    private static final Logger LOGGER = LogManager.getLogger(RuleNameService.class);

    private final RuleNameRepository ruleNameRepository;

    private final DTOConverter dtoConverter;

    private final ModelConverter modelConverter;

    @Autowired
    public RuleNameService(final RuleNameRepository ruleNameRepository, final DTOConverter dtoConverter,
                           final ModelConverter modelConverter) {
        this.ruleNameRepository = ruleNameRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    public RuleNameDTO addRuleName(final RuleNameDTO ruleNameDTO) {
        LOGGER.debug("Inside RuleNameService.addRuleName");

        RuleName ruleName = modelConverter.toRuleName(ruleNameDTO);
        RuleName ruleNameSaved = ruleNameRepository.save(ruleName);

        return dtoConverter.toRuleNameDTO(ruleNameSaved);
    }

    public RuleNameDTO updateRuleName(final int ruleNameId, final RuleNameDTO ruleNameDTO) {
        LOGGER.debug("Inside RuleNameService.updateRuleName");

        ruleNameDTO.setId(ruleNameId);
        RuleName ruleName = modelConverter.toRuleName(ruleNameDTO);
        RuleName ruleNameUpdated = ruleNameRepository.save(ruleName);

        return dtoConverter.toRuleNameDTO(ruleNameUpdated);
    }

    public void deleteRuleName(final int ruleNameId) {
        LOGGER.debug("Inside RuleNameService.deleteRuleName");

        RuleName ruleName = ruleNameRepository.findById(ruleNameId).orElseThrow(() ->
                new ResourceNotFoundException("No RuleName registered with this id"));

        ruleNameRepository.delete(ruleName);

    }

    public RuleNameDTO getRuleNameById(final int ruleNameId) {
        LOGGER.debug("Inside RuleNameService.getRuleNameById");

        RuleName ruleName = ruleNameRepository.findById(ruleNameId).orElseThrow(() ->
                new ResourceNotFoundException("No ruleName registered with this id"));

        return dtoConverter.toRuleNameDTO(ruleName);
    }

    public List<RuleNameDTO> getAllRuleName() {
        LOGGER.debug("Inside RuleNameService.getAllRuleName");

        List<RuleName> ruleNames = ruleNameRepository.findAll();
        List<RuleNameDTO> ruleNameList = new ArrayList<>();

        for (RuleName ruleName : ruleNames) {
            RuleNameDTO ruleNameDTO = dtoConverter.toRuleNameDTO(ruleName);
            ruleNameList.add(ruleNameDTO);
        }

        return ruleNameList;
    }
}
