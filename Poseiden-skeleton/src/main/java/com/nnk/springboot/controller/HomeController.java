package com.nnk.springboot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

	@GetMapping("/")
	public String home() {
		LOGGER.debug("GET Request on /");

		return "home";
	}

	@GetMapping("/admin/home")
	public String adminHome() {
		LOGGER.debug("GET Request on /admin/home");

		return "redirect:/bidList/list";
	}

}
