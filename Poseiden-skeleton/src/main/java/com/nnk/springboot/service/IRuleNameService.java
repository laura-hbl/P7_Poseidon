package com.nnk.springboot.service;

import com.nnk.springboot.dto.RuleNameDTO;

import java.util.List;

/**
 * RuleNameService interface.
 *
 * @author Laura Habdul
 */
public interface IRuleNameService {

    /**
     * Adds a new ruleName in database.
     *
     * @param ruleName the ruleName to be added
     * @return The ruleName saved converted to a RuleNameDTO object
     */
    RuleNameDTO addRuleName(final RuleNameDTO ruleName);

    /**
     * Updates an ruleName based on the given id.
     *
     * @param ruleNameId id of the ruleName to be updated
     * @param ruleName   the ruleName to be updated
     * @return The ruleName updated converted to a RuleNameDTO object
     */
    RuleNameDTO updateRuleName(final int ruleNameId, final RuleNameDTO ruleName);

    /**
     * Deletes an ruleName based on the given id.
     *
     * @param ruleNameId id of the ruleName to be deleted
     */
    void deleteRuleName(final int ruleNameId);

    /**
     * Retrieves an ruleName based on the given id.
     *
     * @param ruleNameId id of the ruleName to be found
     * @return The ruleName retrieved converted to a RuleNameDTO object
     */
    RuleNameDTO getRuleNameById(final int ruleNameId);

    /**
     * Retrieves the ruleName list.
     *
     * @return The ruleName list retrieved where each RuleName is converted to a RuleNameDTO object
     */
    List<RuleNameDTO> getAllRuleName();
}
