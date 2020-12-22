package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controller.UserController;
import com.nnk.springboot.dto.UserDTO;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private UserDTO user1DTO;

    private UserDTO user2DTO;

    @BeforeEach
    public void setUp() {
        user1DTO = new UserDTO( "Laura", "passwordA111&", "Laura", "USER");
        user2DTO = new UserDTO( "Jean", "passwordA222&", "Jean", "USER");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("Home")
    @DisplayName("When home request, then display user list form")
    public void whenHomeRequest_thenDisplayUserListForm() throws Exception {
        when(userService.getAllUser()).thenReturn(Arrays.asList(user1DTO, user2DTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list"))
                .andExpect(status().isOk());

        verify(userService).getAllUser();
    }

    @Test
    @Tag("AddUser")
    @DisplayName("When addUser request, then display user add form")
    public void whenAddUserRequest_thenDisplayUserAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(view().name("user/add"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given a valid user to add, when validate request, then redirect to user list page")
    void givenAValidUserToAdd_whenValidate_thenRedirectToUserListPage() throws Exception {
        when(userService.addUser(any(UserDTO.class))).thenReturn(any(UserDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                .sessionAttr("userDTO", user1DTO)
                .param("username", user1DTO.getUsername())
                .param("password", user1DTO.getPassword())
                .param("fullname", user1DTO.getFullname())
                .param("role", user1DTO.getRole()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).addUser(any(UserDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Give empty username field, when validate request, then return error message and user add form")
    void givenAnEmptyUserNameField_whenValidate_thenReturnErrorMessageAndUserAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                .sessionAttr("userDTO", user1DTO)
                .param("username", "")
                .param("password", user1DTO.getPassword())
                .param("fullname", user1DTO.getFullname())
                .param("role", user1DTO.getRole()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Username is mandatory");
        verify(userService, times(0)).addUser(any(UserDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given invalid password, when validate request, then return error message and user add form")
    void givenAnInvalidPassword_whenValidate_thenReturnErrorMessageAndUserAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                .sessionAttr("userDTO", user1DTO)
                .param("username", user1DTO.getUsername())
                .param("password", "password")
                .param("fullname", user1DTO.getFullname())
                .param("role", user1DTO.getRole()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("The password must contain at least 8 characters, one uppercase letter, " +
                "one number and one symbol");
        verify(userService, times(0)).addUser(any(UserDTO.class));
    }

    @Test
    @Tag("ShowUpdateForm")
    @DisplayName("When showUpdateForm request, then display user update form")
    public void whenShowUpdateFormRequest_thenDisplayUserUpdateForm() throws Exception {
        when(userService.getUserById(1)).thenReturn(user1DTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(view().name("user/update"))
                .andExpect(status().isOk());

        verify(userService).getUserById(1);
    }

    @Test
    @Tag("UpdateUser")
    @DisplayName("Given a valid user to update, when updateUser request, then redirect to user list page")
    void givenAValidUserToUpdate_whenUpdateUser_thenRedirectToUserListPage() throws Exception {
        UserDTO userUpdateDTO = new UserDTO( "Laura", "passwordA111&", "LauraHabdul", "USER");
        when(userService.updateUser(1, user1DTO)).thenReturn(userUpdateDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1")
                .sessionAttr("userDTO", user1DTO)
                .param("username", user1DTO.getUsername())
                .param("password", user1DTO.getPassword())
                .param("fullname", user1DTO.getFullname())
                .param("role", user1DTO.getRole()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).updateUser(anyInt(), any(UserDTO.class));
    }

    @Test
    @Tag("UpdateUser")
    @DisplayName("Given empty fullname field, when updateUser request, then return error message and user update form")
    void givenEmptyFullNameField_whenUpdateUser_thenReturnErrorMessageAndUserUpdateForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1")
                .sessionAttr("userDTO", user1DTO)
                .param("username", user1DTO.getUsername())
                .param("password", user1DTO.getPassword())
                .param("fullname", "")
                .param("role", user1DTO.getRole()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/update"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Fullname is mandatory");
        verify(userService, times(0)).updateUser(anyInt(), any(UserDTO.class));
    }

    @Test
    @Tag("DeleteUser")
    @DisplayName("Given an user to delete, when delete request, then redirect to user list page")
    void givenAnUserToDelete_whenDeleteUser_thenRedirectToUserListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1"))
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).deleteUser(1);
    }
}
