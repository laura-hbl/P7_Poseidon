package com.nnk.springboot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

	@GetMapping("/")
	public String home(final Model model)
	{
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(final Model model)
	{
		return "redirect:/bidList/list";
	}

}
