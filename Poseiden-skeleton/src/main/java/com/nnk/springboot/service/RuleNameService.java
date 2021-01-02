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

/**
 * Contains methods that allow interaction between RuleName business logic and RuleName repository.
 *
 * @author Laura Habdul
 */
@Service
public class RuleNameService implements IRuleNameService {

    /**
     * RuleNameService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(RuleNameService.class);

    /**
     * RuleNameRepository instance.
     */
    private final RuleNameRepository ruleNameRepository;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * Constructor of class RuleNameService.
     * Initialize ruleNameRepository, dtoConverter and modelConverter.
     *
     * @param ruleNameRepository ruleNameRepository instance
     * @param dtoConverter       DTOConverter instance
     * @param modelConverter     ModelConverter instance
     */
    @Autowired
    public RuleNameService(final RuleNameRepository ruleNameRepository, final DTOConverter dtoConverter,
                           final ModelConverter modelConverter) {
        this.ruleNameRepository = ruleNameRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    /**
     * Converts the ruleNameDTO object to add to a RuleName Model object, saved it to database by calling
     * RuleNameRepository's save method. Then, converts the saved ruleName to a RuleNameDTO object.
     *
     * @param ruleNameDTO the ruleName to be added
     * @return The ruleName saved converted to a RuleNameDTO object
     */
    public RuleNameDTO addRuleName(final RuleNameDTO ruleNameDTO) {
        LOGGER.debug("Inside RuleNameService.addRuleName");

        RuleName ruleName = modelConverter.toRuleName(ruleNameDTO);
        RuleName ruleNameSaved = ruleNameRepository.save(ruleName);

        return dtoConverter.toRuleNameDTO(ruleNameSaved);
    }

    /**
     * Checks if the given ruleName to update is registered by calling RuleNameRepository's findById method, if so
     * ruleName found is updated, then saved to database by calling RuleNameRepository's save method and converted
     * to a RuleNameDTO object. Else, ResourceNotFoundException is thrown.
     *
     * @param ruleNameId  id of the ruleName to be updated
     * @param ruleNameDTO the ruleName to be updated
     * @return The ruleName updated converted to a RuleNameDTO object
     */
    public RuleNameDTO updateRuleName(final int ruleNameId, final RuleNameDTO ruleNameDTO) {
        LOGGER.debug("Inside RuleNameService.updateRuleName");

        RuleName ruleName = ruleNameRepository.findById(ruleNameId).orElseThrow(() ->
                new ResourceNotFoundException("No ruleName registered with this id"));

        ruleName.setName(ruleNameDTO.getName());
        ruleName.setDescription(ruleNameDTO.getDescription());
        ruleName.setTemplate(ruleNameDTO.getTemplate());
        ruleName.setJson(ruleNameDTO.getJson());
        ruleName.setSqlStr(ruleNameDTO.getSqlStr());
        ruleName.setSqlPart(ruleNameDTO.getSqlPart());

        RuleName ruleNameUpdated = ruleNameRepository.save(ruleName);

        return dtoConverter.toRuleNameDTO(ruleNameUpdated);
    }

    /**
     * Checks if the given ruleName to delete is registered by calling RuleNameRepository's findById method, if
     * so ruleName found is deleted by calling RuleNameRepository's delete method. Else, ResourceNotFoundException
     * is thrown.
     *
     * @param ruleNameId id of the ruleName to be deleted
     */
    public void deleteRuleName(final int ruleNameId) {
        LOGGER.debug("Inside RuleNameService.deleteRuleName");

        ruleNameRepository.findById(ruleNameId).orElseThrow(() ->
                new ResourceNotFoundException("No ruleName registered with this id"));

        ruleNameRepository.deleteById(ruleNameId);

    }

    /**
     * Calls RuleNameRepository's findById method to retrieves the ruleName with the given id and checks if ruleName
     * exists in database, if so converts the found RuleName to RuleNameDTO object. Else, ResourceNotFoundException
     * is thrown.
     *
     * @param ruleNameId id of the ruleName to be found
     * @return The ruleName found converted to an RuleNameDTO object
     */
    public RuleNameDTO getRuleNameById(final int ruleNameId) {
        LOGGER.debug("Inside RuleNameService.getRuleNameById");

        RuleName ruleName = ruleNameRepository.findById(ruleNameId).orElseThrow(() ->
                new ResourceNotFoundException("No ruleName registered with this id"));

        return dtoConverter.toRuleNameDTO(ruleName);
    }

    /**
     * Retrieves all ruleNames by calling RuleNameRepository's findAll() method and each ruleName from the list is
     * converted to an RuleNameDTO object and added to an ArrayList.
     *
     * @return The ruleName list
     */
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
