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

/**
 * Contains methods that allow interaction between User business logic and User repository.
 *
 * @author Laura Habdul
 */
@Service
public class UserService implements IUserService {

    /**
     * CurvePointService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    /**
     * CurvePointRepository instance.
     */
    private final UserRepository userRepository;

    /**
     * BCryptPasswordEncoder instance.
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * Constructor of class UserService.
     * Initialize userRepository, dtoConverter and passwordEncoder.
     *
     * @param userRepository  UserRepository instance
     * @param passwordEncoder BCryptPasswordEncoder instance
     * @param dtoConverter    DTOConverter instance
     */
    @Autowired
    public UserService(final UserRepository userRepository, final BCryptPasswordEncoder passwordEncoder,
                       final DTOConverter dtoConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.dtoConverter = dtoConverter;
    }


    /**
     * Calls UserRepository's findByUsername method to retrieves the user with the username and checks if user is not
     * already registered, if so DataAlreadyRegisteredException is thrown. Else, encrypts the user password by calling
     * passwordEncoder's encode method, adds data of the given userDTO object to model object to save it by calling
     * UserRepository's save method and then converts the saved User model object to UserDTO object.
     *
     * @param userDTO the user to be registered
     * @return The user saved converted to a UserDTO object
     */
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

    /**
     * Checks if the given user to update is registered by calling UserRepository's findById method, if so user found
     * is updated, then saved to database by calling UserRepository's save method and converted to a UserDTO object.
     * Else, ResourceNotFoundException is thrown.
     *
     * @param userId  id of the user to be updated
     * @param userDTO the user to be updated
     * @return The user updated converted to a UserDTO object
     */
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

    /**
     * Checks if the given user to delete is registered by calling UserRepository's findById method, if so user found
     * is deleted by calling UserRepository's delete method. Else, ResourceNotFoundException is thrown.
     *
     * @param userId id of the user to be deleted
     */
    public void deleteUser(final int userId) {
        LOGGER.debug("Inside UserService.deleteUser");

        userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("No user registered with this id"));

        userRepository.deleteById(userId);
    }

    /**
     * Calls UserRepository's findById method to retrieves the user with the given id and checks if user exists in
     * database, if not ResourceNotFoundException is thrown. Then converts the found User to UserDTO object.
     *
     * @param userId id of the user to be found
     * @return The user found converted to an UserDTO object
     */
    public UserDTO getUserById(final int userId) {
        LOGGER.debug("Inside UserService.getUserById");
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("No user registered with this id"));

        return dtoConverter.toUserDTO(user);
    }

    /**
     * Retrieves all users by calling UserRepository's findAll() method, if user list is empty
     * ResourceNotFoundException is thrown. Else, each user from the list is converted to an UserDTO object and
     * added to an ArrayList.
     *
     * @return The user list
     */
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
