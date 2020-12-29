package com.nnk.springboot.service;

import com.nnk.springboot.dto.UserDTO;

import java.util.List;

/**
 * UserService interface.
 *
 * @author Laura Habdul
 */
public interface IUserService {

    /**
     * Registers a new user in database.
     *
     * @param user the user to be registered
     * @return The user saved converted to an UserDTO object
     */
    UserDTO addUser(final UserDTO user);

    /**
     * Updates an user based on the given id.
     *
     * @param userId id of the user to be updated
     * @param user   the user to be updated
     * @return The user updated converted to an UserDTO object
     */
    UserDTO updateUser(final int userId, final UserDTO user);

    /**
     * Deletes an user based on the given id.
     *
     * @param userId id of the user to be deleted
     */
    void deleteUser(final int userId);

    /**
     * Retrieves an user based on the given id.
     *
     * @param userId id of the user to be found
     * @return The user retrieved converted to an UserDTO object
     */
    UserDTO getUserById(final int userId);

    /**
     * Retrieves the user list.
     *
     * @return The user list retrieved where each User is converted to an UserDTO object
     */
    List<UserDTO> getAllUser();
}
