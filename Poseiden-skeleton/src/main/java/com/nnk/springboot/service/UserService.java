package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataAlreadyRegisteredException;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.util.DTOConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final DTOConverter dtoConverter;


    @Autowired
    public UserService(final UserRepository userRepository, final BCryptPasswordEncoder passwordEncoder,
                       final DTOConverter dtoConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.dtoConverter = dtoConverter;
    }

    public UserDTO addUser(final UserDTO userDTO) {
        LOGGER.debug("Inside UserService.addUser");
        User userFound = userRepository.findByUsername(userDTO.getUsername());

        if (userFound != null) {
            throw new DataAlreadyRegisteredException("The username provided may be registered already");
        }
        String password = passwordEncoder.encode(userDTO.getPassword());
        User userToSave = new User(userDTO.getUsername(), password, userDTO.getFullname(), userDTO.getRole());

        User user = userRepository.save(userToSave);

        return dtoConverter.toUserDTO(user);
    }

    public UserDTO updateUser(final int userId, final UserDTO userDTO) {
        LOGGER.debug("Inside UserService.updateUser");

        User userToUpdate = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("No user registered with this id"));

        userToUpdate.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userToUpdate.setFullname(userDTO.getFullname());
        userToUpdate.setId(userId);

        User user = userRepository.save(userToUpdate);

        return dtoConverter.toUserDTO(user);
    }

    public void deleteUser(final int userId) {
        LOGGER.debug("Inside UserService.deleteUser");

        userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("No user registered with this id"));

        userRepository.deleteById(userId);
    }

    public UserDTO getUserById(final int userId) {
        LOGGER.debug("Inside UserService.getUserById");
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("No user registered with this id"));

        return dtoConverter.toUserDTO(user);
    }

    public List<UserDTO> getAllUser() {
        LOGGER.debug("Inside UserService.getAllUser");
        List<User> users = userRepository.findAll();
        List<UserDTO> userList = new ArrayList<>();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("Failed to get user list");
        }

        for (User user : users) {
            UserDTO userDTO = dtoConverter.toUserDTO(user);
            userList.add(userDTO);
        }

        return userList;
    }
}
