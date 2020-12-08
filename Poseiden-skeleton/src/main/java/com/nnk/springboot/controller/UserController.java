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

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final IUserService userService;

    @Autowired
    public UserController(final IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String home(final Model model) {
        LOGGER.debug("GET Request on /user/list");

        model.addAttribute("users", userService.getAllUser());

        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(final Model model) {
        LOGGER.debug("GET Request on /user/add");

        model.addAttribute("user", new UserDTO());

        return "user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final UserDTO userDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /user/validate with username {}", userDTO.getUsername());

        if (result.hasErrors()) {
            return "user/add";
        }
        userService.addUser(userDTO);

        return "redirect:/user/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer userId, final Model model) {
        LOGGER.debug("GET Request on /user/update/{id} with id : {}", userId);

        UserDTO user = userService.getUserById(userId);
        model.addAttribute("user", user);

        return "user/update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") final Integer userId, @Valid final UserDTO userDTO,
                             final BindingResult result) {
        LOGGER.debug("POST Request on /user/update/{id} with id : {}", userId);

        if (result.hasErrors()) {
            return "user/update";
        }
        userService.updateUser(userId, userDTO);

        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") final Integer userId) {
        LOGGER.debug("GET Request on /user/delete/{id} with id : {}", userId);

        userService.deleteUser(userId);

        return "redirect:/user/list";
    }
}
