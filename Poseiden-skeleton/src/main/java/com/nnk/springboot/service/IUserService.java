package com.nnk.springboot.service;

import com.nnk.springboot.dto.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO addUser(final UserDTO user);

    UserDTO updateUser(final int userId, final UserDTO user);

    void deleteUser(final int userId);

    UserDTO getUserById(final int userId);

    List<UserDTO> getAllUser();

}
