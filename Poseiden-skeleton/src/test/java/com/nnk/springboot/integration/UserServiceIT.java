package com.nnk.springboot.integration;

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataAlreadyRegisteredException;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.UserService;
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
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Test
    @Tag("AddUser")
    @DisplayName("If user is not registered, when addUser, then return user saved")
    public void givenAnUnRegisteredUser_whenAddUser_thenUserSavedShouldBeReturned() {
        UserDTO userDTO = new UserDTO( "Laura", "passwordA111&", "Laura", "USER");

        UserDTO userSaved = userService.addUser(userDTO);

        assertNotNull(userSaved);
        assertThat(userSaved.getFullname()).isEqualTo("Laura");
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("AddUser")
    @DisplayName("If user is already registered, when addUser, then throw DataAlreadyRegisteredException")
    public void givenARegisteredUser_whenAddUser_thenDataAlreadyRegisteredExceptionIsThrown() {
        UserDTO user = new UserDTO("User", "user",
                "$2a$10$/Oa835whQGgbbHEl4lyyDe0IWlREZb69.ikdiCzUco81yOwx3W07m", "USER");

        userService.addUser(user);
    }

    @Test
    @Tag("UpdateUser")
    @DisplayName("Given an user to update, when updateUser, then return user updated")
    public void givenAnUserToUpdate_whenUpdateUser_thenUserUpdatedShouldBeReturned() {
        UserDTO userToUpdate = new UserDTO( "user", "$2a$10$/Oa835whQGgbbHEl4lyyDe0IWlREZb69.ikdiCzUco81yOwx3W07m",
                "userUpdate", "USER");

        UserDTO userUpdated = userService.updateUser(2, userToUpdate);

        assertNotNull(userUpdated);
        assertThat(userUpdated.getFullname()).isEqualTo("userUpdate");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateUser")
    @DisplayName("If user id cant be found, when updateUser, then throw ResourceNotFoundException")
    public void givenUnFoundUserId_whenUpdateUser_thenResourceNotFoundExceptionIsThrown() {
        UserDTO userToUpdate = new UserDTO( "user", "$2a$10$/Oa835whQGgbbHEl4lyyDe0IWlREZb69.ikdiCzUco81yOwx3W07m",
                "user", "USER");

        userService.updateUser(6, userToUpdate);
    }

    @Test
    @Tag("DeleteUser")
    @DisplayName("Given an user to delete, when deleteUser, then user should be delete successfully")
    public void givenAnUserId_whenDeleteUser_thenUserShouldBeDeleteSuccessfully() {
        userService.deleteUser(2);

        assertThrows(ResourceNotFoundException.class, () -> { userService.deleteUser(2);
        });
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteUser")
    @DisplayName("If user id cant be found, when deleteUser, then throw ResourceNotFoundException")
    public void givenUnFoundUserId_whenDeleteUser_thenResourceNotFoundExceptionIsThrown() {
        userService.deleteUser(6);
    }

    @Test
    @Tag("GetUserById")
    @DisplayName("Given an user id, when getUserById, then expected user should be returned")
    public void givenAnUserId_whenGetUserById_thenExpectedUserShouldBeReturned() {
        UserDTO user = userService.getUserById(2);

        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo("user");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetUserById")
    @DisplayName("If user id cant be found, when getUserById, then throw ResourceNotFoundException")
    public void givenUnFoundUserId_whenGetUserById_thenResourceNotFoundExceptionIsThrown() {
        userService.getUserById(6);
    }

    @Test
    @Tag("GetAllUser")
    @DisplayName("When getAllUser, then user list should be returned")
    public void whenGetAllUser_thenUserListShouldBeReturned() {
        List<UserDTO> users = userService.getAllUser();

        assertNotNull(users);
        assertThat(users.size()).isEqualTo(2);
    }
}
