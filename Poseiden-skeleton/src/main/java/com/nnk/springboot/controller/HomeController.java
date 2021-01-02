package com.nnk.springboot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Creates endpoints for homepage access.
 *
 * @author Laura Habdul
 */
@Controller
public class HomeController {

    /**
     * HomeController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

    /**
     * Displays user home page.
     *
     * @return The reference to the home HTML page
     */
    @GetMapping("/")
    public String showHomePage() {
        LOGGER.debug("GET Request on /");

        return "home";
    }

    /**
     * Displays admin home page.
     *
     * @return The redirect to /bidList/list endpoint
     */
    @GetMapping("/admin/home")
    public String showAdminHomePage() {
        LOGGER.debug("GET Request on /admin/home");

        return "redirect:/bidList/list";
    }
}
