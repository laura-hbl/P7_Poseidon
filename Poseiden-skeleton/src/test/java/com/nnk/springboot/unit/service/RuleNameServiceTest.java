package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import com.nnk.springboot.util.DTOConverter;
import com.nnk.springboot.util.ModelConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RuleNameServiceTest {

    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    private static RuleName ruleName1;

    private static RuleName ruleName2;

    private static RuleNameDTO ruleName1DTO;

    private static RuleNameDTO ruleName2DTO;

    private static List<RuleNameDTO> ruleNameListDTO;

    @Before
    public void setUp() {
        ruleName1DTO = new RuleNameDTO(1, "name", "description","json",
                "template", "sqlStr", "sqlPart");
        ruleName2DTO = new RuleNameDTO(2, "name", "description","json",
                "template", "sqlStr", "sqlPart");
        ruleName1 = new RuleName(1, "name", "description","json",
                "template", "sqlStr", "sqlPart");
        ruleName2 = new RuleName(2, "name", "description","json",
                "template", "sqlStr", "sqlPart");
        ruleNameListDTO = Arrays.asList(ruleName1DTO, ruleName2DTO);
    }

    @Test
    @Tag("AddRuleName")
    @DisplayName("Given a ruleName to save, when addRuleName, then RuleName should be saved correctly")
    public void givenARuleName_whenAddRuleName_thenRuleNameShouldBeSavedCorrectly() {
        RuleNameDTO ruleNameToAddDTO = new RuleNameDTO("name", "description","json",
                "template", "sqlStr", "sqlPart");
        RuleName ruleNameToAdd = new RuleName("name", "description","json",
                "template", "sqlStr", "sqlPart");
        when(modelConverter.toRuleName(any(RuleNameDTO.class))).thenReturn(ruleNameToAdd);
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName1);
        when(dtoConverter.toRuleNameDTO(any(RuleName.class))).thenReturn(ruleName1DTO);

        RuleNameDTO ruleNameSaved = ruleNameService.addRuleName(ruleNameToAddDTO);

        assertThat(ruleNameSaved).isEqualToComparingFieldByField(ruleName1DTO);
        InOrder inOrder = inOrder(ruleNameRepository, dtoConverter, modelConverter);
        inOrder.verify(modelConverter).toRuleName(any(RuleNameDTO.class));
        inOrder.verify(ruleNameRepository).save(any(RuleName.class));
        inOrder.verify(dtoConverter).toRuleNameDTO(any(RuleName.class));
    }

    @Test
    @Tag("UpdateRuleName")
    @DisplayName("Given a registered RuleName, when updateRuleName, then RuleName should be updated correctly")
    public void givenARuleNameToUpdate_whenUpdateRuleName_thenRuleNameShouldBeUpdateCorrectly() {
        RuleNameDTO ruleName1DTOUpdated = new RuleNameDTO(1, "nameUpdate", "description","json",
                "template", "sqlStr", "sqlPart");
        RuleName ruleName1Updated = new RuleName(1, "nameUpdate", "description","json",
                "template", "sqlStr", "sqlPart");
        when(ruleNameRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(ruleName1));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName1Updated);
        when(dtoConverter.toRuleNameDTO(any(RuleName.class))).thenReturn(ruleName1DTOUpdated);

        RuleNameDTO result = ruleNameService.updateRuleName(1, new RuleNameDTO("nameUpdate",
                "description","json", "template", "sqlStr", "sqlPart"));

        assertThat(result).isEqualTo(ruleName1DTOUpdated);
        InOrder inOrder = inOrder(ruleNameRepository, dtoConverter, modelConverter);
        inOrder.verify(ruleNameRepository).findById(anyInt());
        inOrder.verify(ruleNameRepository).save(any(RuleName.class));
        inOrder.verify(dtoConverter).toRuleNameDTO(any(RuleName.class));
    }

    @Test
    @Tag("DeleteRuleName")
    @DisplayName("Given a RuleName Id, when deleteRuleName, then delete process should be done in correct order")
    public void givenARuleNameId_whenDeleteRuleName_thenDeletingShouldBeDoneInCorrectOrder() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(ruleName1));

        ruleNameService.deleteRuleName(anyInt());

        InOrder inOrder = inOrder(ruleNameRepository);
        inOrder.verify(ruleNameRepository).findById(anyInt());
        inOrder.verify(ruleNameRepository).deleteById(anyInt());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteRuleName")
    @DisplayName("If RuleName can't be found, when deleteRuleName, then throw ResourceNotFoundException")
    public void givenUnFoundRuleName_whenDeleteRuleName_thenResourceNotFoundExceptionIsThrown() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        ruleNameService.deleteRuleName(anyInt());
    }

    @Test
    @Tag("GetRuleNameById")
    @DisplayName("Given a RuleName id, when getRuleNameById, then expected RuleName should be returned correctly")
    public void givenARuleNameId_whenGetRuleNameById_thenExpectedRuleNameShouldBeReturnCorrectly() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(ruleName1));
        when(dtoConverter.toRuleNameDTO(any(RuleName.class))).thenReturn(ruleName1DTO);

        RuleNameDTO ruleNameFound = ruleNameService.getRuleNameById(1);

        assertThat(ruleNameFound).isEqualToComparingFieldByField(ruleName1DTO);
        InOrder inOrder = inOrder(ruleNameRepository, dtoConverter);
        inOrder.verify(ruleNameRepository).findById(anyInt());
        inOrder.verify(dtoConverter).toRuleNameDTO(any(RuleName.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetRuleNameById")
    @DisplayName("If ruleName can't be found, when getRuleNameById, then throw ResourceNotFoundException")
    public void givenUnFoundRuleName_whenGetRuleNameById_thenResourceNotFoundExceptionIsThrown() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        ruleNameService.getRuleNameById(1);
    }

    @Test
    @Tag("GetAllRuleName")
    @DisplayName("Given an RuleName list, when getAllRuleName, then result should match expected RuleName list")
    public void givenARuleNameList_whenGetAllRuleName_thenReturnExpectedRuleNameList() {
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName1, ruleName2));

        when(dtoConverter.toRuleNameDTO(ruleName1)).thenReturn(ruleName1DTO);
        when(dtoConverter.toRuleNameDTO(ruleName2)).thenReturn(ruleName2DTO);

        List<RuleNameDTO> result = ruleNameService.getAllRuleName();

        assertThat(result).isEqualTo(ruleNameListDTO);
        InOrder inOrder = inOrder(ruleNameRepository, dtoConverter);
        inOrder.verify(ruleNameRepository).findAll();
        inOrder.verify(dtoConverter).toRuleNameDTO(ruleName1);
        inOrder.verify(dtoConverter).toRuleNameDTO(ruleName2);
    }
}
