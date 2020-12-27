package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controller.LoginController;
import com.nnk.springboot.dto.LoginDTO;
import com.nnk.springboot.security.MyUserDetailsService;
import com.nnk.springboot.security.jwt.JwtUtils;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {


    @MockBean
    private UserService userService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private LoginDTO loginDTO;

    @BeforeEach
    public void setUp() {
        loginDTO = new LoginDTO("Laura", "password123A*");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("ShowLoginForm")
    @DisplayName("When showLoginForm request, then display login form")
    public void whenShowLoginFormRequest_thenDisplayLoginForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(model().attributeExists("loginDTO"))
                .andExpect(view().name("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("AuthenticateUser")
    @DisplayName("Given valid credentials, when authenticateUser request, then redirect to bid list page")
    void givenValidCredentials_whenAuthenticateUser_thenRedirectToBidListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                .accept(MediaType.ALL)
                .param("username", loginDTO.getUsername())
                .param("password", loginDTO.getPassword()))
                .andExpect(model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"))
                .andReturn();
    }

    @Test
    @Tag("AuthenticateUser")
    @DisplayName("Given empty username field, when authenticateUser request, then return error message and login page")
    void givenEmptyUsernameField_whenAuthenticateUser_thenReturnErrorMessageAndLoginPage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                .accept(MediaType.ALL)
                .param("username","")
                .param("password", loginDTO.getPassword()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("/login"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Username is mandatory");
    }

    @Test
    @Tag("ShowError403")
    @DisplayName("When showError403 request, then display error page")
    public void whenErrorRequest_thenDisplayError403Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/403"))
                .andExpect(view().name("/403"))
                .andExpect(status().isOk());
    }
}
