package com.nnk.springboot.controller;

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.service.IUserService;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Creates REST endpoints for crud operations on User data.
 *
 * @author Laura Habdul
 * @see IUserService
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * UserController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    /**
     * IUserService's implement class reference.
     */
    private final IUserService userService;

    /**
     * Constructor of class UserController.
     * Initialize userService.
     *
     * @param userService IUserService's implement class reference
     */
    @Autowired
    public UserController(final IUserService userService) {
        this.userService = userService;
    }

    /**
     * Displays user list.
     *
     * @param model makes user list objects available to user/list HTML page
     * @return The reference to the user/list HTML page
     */
    @GetMapping("/list")
    public String showUserList(final Model model) {
        LOGGER.debug("GET Request on /user/list");

        model.addAttribute("users", userService.getAllUser());

        LOGGER.info("GET Request on /user/list - SUCCESS");
        return "user/list";
    }

    /**
     * Displays form for adding a new user.
     *
     * @param model makes a new UserDTO object available to user/add HTML page
     * @return The reference to the user/add HTML page
     */
    @GetMapping("/add")
    public String addUserForm(final Model model) {
        LOGGER.debug("GET Request on /user/add");

        model.addAttribute("userDTO", new UserDTO());

        LOGGER.info("GET Request on /user/add - SUCCESS");
        return "user/add";
    }

    /**
     * Adds a new user.
     *
     * @param userDTO the user to be added
     * @param result  holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the user/add HTML page if result has errors. Else, redirects to /user/list endpoint
     */
    @PostMapping("/validate")
    public String validate(@Valid final UserDTO userDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /user/validate with username {}", userDTO.getUsername());

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "user/add";
        }
        userService.addUser(userDTO);

        LOGGER.info("POST Request on /user/validate with username {} - SUCCESS");
        return "redirect:/user/list";
    }

    /**
     * Displays a form to update an existing user.
     *
     * @param userId id of the user to be updated
     * @param model  makes UserDTO object available to user/update HTML page
     * @return The reference to the user/update HTML page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer userId, final Model model) {
        LOGGER.debug("GET Request on /user/update/{id} with id : {}", userId);

        UserDTO user = userService.getUserById(userId);
        model.addAttribute("userDTO", user);

        LOGGER.info("GET Request on /user/update/{id} - SUCCESS");
        return "user/update";
    }

    /**
     * Updates a saved user.
     *
     * @param userId  id of the user to be updated
     * @param userDTO the user to be updated
     * @param result  holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the user/update HTML page if result has errors. Else, redirects to /user/list endpoint
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") final Integer userId, @Valid final UserDTO userDTO,
                             final BindingResult result) {
        LOGGER.debug("POST Request on /user/update/{id} with id : {}", userId);

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "user/update";
        }
        userService.updateUser(userId, userDTO);

        LOGGER.info("POST Request on /user/update/{id} - SUCCESS");
        return "redirect:/user/list";
    }

    /**
     * Deletes a saved user.
     *
     * @param userId id of the user to be deleted
     * @return The redirect to /user/list endpoint
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") final Integer userId) {
        LOGGER.debug("GET Request on /user/delete/{id} with id : {}", userId);

        userService.deleteUser(userId);

        LOGGER.info("GET Request on /user/delete/{id} - SUCCESS");
        return "redirect:/user/list";
    }
}
