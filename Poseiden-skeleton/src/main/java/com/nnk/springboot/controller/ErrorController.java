package com.nnk.springboot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Creates endpoints for error pages.
 *
 * @author Laura Habdul
 */
@Controller
public class ErrorController {

    /**
     * HomeController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ErrorController.class);

    /**
     * Displays the 403 error page.
     *
     * @return The reference to the 403 HTML page
     */
    @GetMapping("/403")
    public String showError403() {
        LOGGER.debug("GET Request on /403");

        return "/403";
    }

    /**
     * Displays the 404 error page.
     *
     * @return The reference to the 404 HTML page
     */
    @GetMapping("/404")
    public String showError404() {
        LOGGER.debug("GET Request on /404");

        return "/404";
    }
}
