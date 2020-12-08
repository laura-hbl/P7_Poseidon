package com.nnk.springboot.service;

import com.nnk.springboot.dto.RuleNameDTO;

import java.util.List;

public interface IRuleNameService {

    RuleNameDTO addRuleName(final RuleNameDTO ruleName);

    RuleNameDTO updateRuleName(final int ruleNameId, final RuleNameDTO ruleName);

    void deleteRuleName(final int ruleNameId);

    RuleNameDTO getRuleNameById(final int ruleNameId);

    List<RuleNameDTO> getAllRuleName();
}
