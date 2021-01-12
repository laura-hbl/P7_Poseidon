package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controller.ErrorController;
import com.nnk.springboot.security.jwt.JwtUtils;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ErrorController.class)
public class ErrorControllerTest {

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("ShowError403")
    @DisplayName("When showError403 request, then display error page")
    public void whenErrorRequest_thenDisplayError403Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/403"))
                .andExpect(view().name("/403"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("ShowError404")
    @DisplayName("When showError404 request, then display error page")
    public void whenErrorRequest_thenDisplayError404Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/404"))
                .andExpect(view().name("/404"))
                .andExpect(status().isOk());
    }
}
