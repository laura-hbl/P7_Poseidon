package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataAlreadyRegisteredException;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    private static User user1;

    private static User user2;

    private static UserDTO user1DTO;

    private static UserDTO user2DTO;

    private static List<UserDTO> userListDTO;

    @Before
    public void setUp() {
        user1DTO = new UserDTO(1, "Laura", "passwordA111&", "Laura", "USER");
        user2DTO = new UserDTO(2, "Herve", "passwordB222&", "Herve", "ADMIN");
        user1 = new User(1, "Laura", "passwordA111&", "Laura", "USER");
        user2 = new User(2, "Herve", "passwordB222&", "Herve", "ADMIN");
        userListDTO = Arrays.asList(user1DTO, user2DTO);
    }


    @Test
    @Tag("AddUser")
    @DisplayName("If user is not registered, when addUser, then user should be saved correctly")
    public void givenAnUnRegisteredUser_whenAddUser_thenUserShouldBeSavedCorrectly() {
        User userToAdd = new User(1, "Laura", "HjuIY9jk5op&tc", "Laura", "USER");
        UserDTO userToAddDTO = new UserDTO(1, "Laura", "HjuIY9jk5op&tc", "Laura",
                "USER");
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(modelConverter.toUser(any(UserDTO.class))).thenReturn(new User("Laura", "passwordA111&",
                "Laura", "USER"));
        when(passwordEncoder.encode(anyString())).thenReturn("HjuIY9jk5op&tc");
        when(userRepository.save(any(User.class))).thenReturn(userToAdd);
        when(dtoConverter.toUserDTO(any(User.class))).thenReturn(userToAddDTO);

        UserDTO userSaved = userService.addUser(new UserDTO("Laura", "passwordA111&",
                "Laura", "USER"));

        assertThat(userSaved).isEqualToComparingFieldByField(userToAddDTO);
        InOrder inOrder = inOrder(userRepository, passwordEncoder, dtoConverter);
        inOrder.verify(userRepository).findByUsername(anyString());
        inOrder.verify(passwordEncoder).encode(anyString());
        inOrder.verify(userRepository).save(any(User.class));
        inOrder.verify(dtoConverter).toUserDTO(any(User.class));
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("AddUser")
    @DisplayName("If user is already registered, when AddUser, then throw DataAlreadyRegisteredException")
    public void givenARegisteredUser_whenAddUser_thenDataAlreadyRegisteredExceptionIsThrown() {
        when(userRepository.findByUsername(anyString())).thenReturn(user1);

        userService.addUser(user1DTO);
    }

    @Test
    @Tag("UpdateUser")
    @DisplayName("Given a registered User, when updateUser, then User should be updated correctly")
    public void givenARegisteredUser_whenUpdateUser_thenUserShouldBeUpdateCorrectly() {
        User userUpdated = new User(1, "Laura", "HjuIY9jk5op&tc&", "LauraHbl", "USER");
        UserDTO userUpdatedDTO = new UserDTO(1, "Laura", "HjuIY9jk5op&tc", "LauraHbl",
                "USER");
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(user1));
        when(modelConverter.toUser(any(UserDTO.class))).thenReturn(new User("Laura", "passwordA111&",
                "LauraHbl", "USER"));
        when(passwordEncoder.encode(anyString())).thenReturn("HjuIY9jk5op&tc");
        when(userRepository.save(any(User.class))).thenReturn(userUpdated);
        when(dtoConverter.toUserDTO(any(User.class))).thenReturn(userUpdatedDTO);

        UserDTO result = userService.updateUser(1, new UserDTO("Laura", "passwordA111&",
                "LauraHbl", "USER"));

        assertThat(result).isEqualTo(userUpdatedDTO);
        InOrder inOrder = inOrder(userRepository, modelConverter, passwordEncoder, dtoConverter);
        inOrder.verify(userRepository).findById(anyInt());
        inOrder.verify(modelConverter).toUser(any(UserDTO.class));
        inOrder.verify(passwordEncoder).encode(anyString());
        inOrder.verify(userRepository).save(any(User.class));
        inOrder.verify(dtoConverter).toUserDTO(any(User.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateUser")
    @DisplayName("If User cant be found, when updateUser, then throw ResourceNotFoundException")
    public void givenUnFoundUser_whenUpdateUser_thenResourceNotFoundExceptionIsThrown() {
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        userService.updateUser(1, user1DTO);
    }

    @Test
    @Tag("DeleteUser")
    @DisplayName("Given User Id, when deleteUser, then delete process should be done in correct order")
    public void givenAUserId_whenDeleteUser_thenDeletingShouldBeDoneInCorrectOrder() {
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(user1));

        userService.deleteUser(anyInt());

        InOrder inOrder = inOrder(userRepository);
        inOrder.verify(userRepository).findById(anyInt());
        inOrder.verify(userRepository).deleteById(anyInt());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteUser")
    @DisplayName("If User can't be found, when deleteUser, then throw ResourceNotFoundException")
    public void givenUnFoundUser_whenDeleteUser_thenResourceNotFoundExceptionIsThrown() {
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        userService.deleteUser(anyInt());
    }

    @Test
    @Tag("GetUserById")
    @DisplayName("Given an user id, when getUserById, then expected user should be returned correctly")
    public void givenAnUserId_whenGetUserById_thenExpectedUserShouldBeReturnCorrectly() {
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(user1));
        when(dtoConverter.toUserDTO(any(User.class))).thenReturn(user1DTO);

        UserDTO userFound = userService.getUserById(1);

        assertThat(userFound).isEqualToComparingFieldByField(user1DTO);

        InOrder inOrder = inOrder(userRepository, dtoConverter);
        inOrder.verify(userRepository).findById(anyInt());
        inOrder.verify(dtoConverter).toUserDTO(any(User.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetUserById")
    @DisplayName("If User can't be found, when getUserById, then throw ResourceNotFoundException")
    public void givenUnFoundUser_whenGetUserById_thenResourceNotFoundExceptionIsThrown() {
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        userService.getUserById(1);
    }

    @Test
    @Tag("GetAllUser")
    @DisplayName("Given an user list, when getAllUser, then result should match expected user list")
    public void givenAnUserList_whenGetAllUser_thenReturnExpectedUserList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        when(dtoConverter.toUserDTO(user1)).thenReturn(user1DTO);
        when(dtoConverter.toUserDTO(user2)).thenReturn(user2DTO);

        List<UserDTO> result = userService.getAllUser();

        assertThat(result).isEqualTo(userListDTO);
        InOrder inOrder = inOrder(userRepository, dtoConverter);
        inOrder.verify(userRepository).findAll();
        inOrder.verify(dtoConverter).toUserDTO(user1);
        inOrder.verify(dtoConverter).toUserDTO(user2);
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetAllUser")
    @DisplayName("Given an empty user list, when getAllUser, then throw ResourceNotFoundException")
    public void givenAnEmptyUserList_whenGetAllUser_thenResourceNotFoundExceptionIsThrown() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

       userService.getAllUser();
    }
}
