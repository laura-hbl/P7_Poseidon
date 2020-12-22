package com.nnk.springboot.integration;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.RuleNameService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RuleNameServiceIT {

    @Autowired
    private RuleNameService ruleNameService;

    @Test
    @Tag("AddRuleName")
    @DisplayName("If ruleName is not registered, when addRuleName, then return ruleName saved")
    public void givenAnUnRegisteredRuleName_whenAddRuleName_thenRuleNameSavedShouldBeReturned() {
        RuleNameDTO ruleNameDTO = new RuleNameDTO( "name", "description","json",
                        "template", "sqlStr", "sqlPart");

        RuleNameDTO ruleNameSaved = ruleNameService.addRuleName(ruleNameDTO);

        assertNotNull(ruleNameSaved);
        assertThat(ruleNameSaved.getDescription()).isEqualTo("description");
    }

    @Test
    @Tag("UpdateRuleName")
    @DisplayName("Given an ruleName to update, when updateRuleName, then return ruleName updated")
    public void givenAnRuleNameToUpdate_whenUpdateRuleName_thenRuleNameUpdatedShouldBeReturned() {
        RuleNameDTO ruleNameToUpdate = new RuleNameDTO("name", "description","json",
                "template", "sqlStr", "sqlPart");

        RuleNameDTO ruleNameUpdated = ruleNameService.updateRuleName(1, ruleNameToUpdate);

        assertNotNull(ruleNameUpdated);
        assertThat(ruleNameUpdated.getName()).isEqualTo("name");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateRuleName")
    @DisplayName("If RuleName id cant be found, when updateRuleName, then throw ResourceNotFoundException")
    public void givenUnFoundRuleNameId_whenUpdateRuleName_thenResourceNotFoundExceptionIsThrown() {
        RuleNameDTO ruleNameToUpdate = new RuleNameDTO("name", "description","json",
                "template", "sqlStr", "sqlPart");

        ruleNameService.updateRuleName(6, ruleNameToUpdate);
    }

    @Test
    @Tag("DeleteRuleName")
    @DisplayName("Given an ruleName to delete, when deleteRuleName, then ruleName should be delete successfully")
    public void givenAnRuleNameId_whenDeleteRuleName_thenRuleNameShouldBeDeleteSuccessfully() {
        ruleNameService.deleteRuleName(2);

        assertThrows(ResourceNotFoundException.class, () -> { ruleNameService.deleteRuleName(2);
        });
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteRuleName")
    @DisplayName("If ruleName id cant be found, when deleteRuleName, then throw ResourceNotFoundException")
    public void givenUnFoundRuleNameId_whenDeleteRuleName_thenResourceNotFoundExceptionIsThrown() {
        ruleNameService.deleteRuleName(6);
    }

    @Test
    @Tag("GetRuleNameById")
    @DisplayName("Given an ruleName id, when getRuleNameById, then expected ruleName should be returned")
    public void givenAnRuleNameId_whenGetRuleNameById_thenExpectedRuleNameShouldBeReturned() {
        RuleNameDTO ruleName = ruleNameService.getRuleNameById(2);

        assertNotNull(ruleName);
        assertThat(ruleName.getDescription()).isEqualTo("description2");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetRuleNameById")
    @DisplayName("If ruleName id cant be found, when getRuleNameById, then throw ResourceNotFoundException")
    public void givenUnFoundRuleNameId_whenGetRuleNameById_thenResourceNotFoundExceptionIsThrown() {
        ruleNameService.getRuleNameById(6);
    }

    @Test
    @Tag("GetAllRuleName")
    @DisplayName("When getAllRuleName, then ruleName list should be returned")
    public void whenGetAllRuleName_thenRuleNameListShouldBeReturned() {
        List<RuleNameDTO> ruleNames = ruleNameService.getAllRuleName();

        assertNotNull(ruleNames);
        assertThat(ruleNames.size()).isEqualTo(2);
    }
}
