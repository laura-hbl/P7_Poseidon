package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controller.RuleNameController;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.security.jwt.JwtUtils;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @MockBean
    private RuleNameService ruleNameService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private RuleNameDTO ruleName1DTO;

    private RuleNameDTO ruleName2DTO;

    @BeforeEach
    public void setUp() {
        ruleName1DTO = new RuleNameDTO(1, "name", "description","json",
                "template", "sqlStr", "sqlPart");
        ruleName2DTO = new RuleNameDTO(2, "name", "description","json",
                "template", "sqlStr", "sqlPart");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("Home")
    @DisplayName("When home request, then display ruleName list form")
    public void whenHomeRequest_thenDisplayRuleNameListForm() throws Exception {
        when(ruleNameService.getAllRuleName()).thenReturn(Arrays.asList(ruleName1DTO, ruleName2DTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk());

        verify(ruleNameService).getAllRuleName();
    }

    @Test
    @Tag("AddRuleName")
    @DisplayName("When addRuleName request, then display RuleName add form")
    public void whenAddRuleNameRequest_thenDisplayRuleNameAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(model().attributeExists("ruleNameDTO"))
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given a valid RuleName to add, when validate request, then redirect to ruleName list page")
    void givenAValidRuleNameToAdd_whenValidate_thenRedirectToRuleNameListPage() throws Exception {
        when(ruleNameService.addRuleName(any(RuleNameDTO.class))).thenReturn(any(RuleNameDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                .sessionAttr("RuleNameDTO", ruleName1DTO)
                .param("name", ruleName1DTO.getName())
                .param("description", ruleName1DTO.getDescription())
                .param("template", ruleName1DTO.getTemplate())
                .param("sqlStr", ruleName1DTO.getSqlStr())
                .param("sqlPart", ruleName1DTO.getSqlPart()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).addRuleName(any(RuleNameDTO.class));
    }

    @Test
    @Tag("ShowUpdateForm")
    @DisplayName("When showUpdateForm request, then display RuleName update form")
    public void whenShowUpdateFormRequest_thenDisplayRuleNameUpdateForm() throws Exception {
        when(ruleNameService.getRuleNameById(1)).thenReturn(ruleName1DTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(model().attributeExists("ruleNameDTO"))
                .andExpect(view().name("ruleName/update"))
                .andExpect(status().isOk());

        verify(ruleNameService).getRuleNameById(1);
    }

    @Test
    @Tag("UpdateRuleName")
    @DisplayName("Given a valid ruleName to update, when updateRuleName request, then redirect to ruleName list page")
    void givenAValidRuleNameToUpdate_whenUpdateRuleName_thenRedirectToRuleNameListPage() throws Exception {
        RuleNameDTO ruleNameUpdateDTO = new RuleNameDTO("name", "description","json",
                "template", "sqlStr", "sqlPart");
        when(ruleNameService.updateRuleName(1, ruleName1DTO)).thenReturn(ruleNameUpdateDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
                .sessionAttr("ruleNameDTO", ruleName1DTO)
                .param("name", ruleName1DTO.getName())
                .param("description", ruleName1DTO.getDescription())
                .param("template", ruleName1DTO.getTemplate())
                .param("sqlStr", ruleName1DTO.getSqlStr())
                .param("sqlPart", ruleName1DTO.getSqlPart()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).updateRuleName(anyInt(), any(RuleNameDTO.class));
    }

    @Test
    @Tag("DeleteRuleName")
    @DisplayName("Given a ruleName to delete, when deleteRuleName request, then redirect to ruleName list page")
    void givenARuleNameToDelete_whenUpdateRuleName_thenRedirectToRuleNameListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).deleteRuleName(1);
    }
}
