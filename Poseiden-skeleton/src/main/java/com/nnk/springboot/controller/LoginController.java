package com.nnk.springboot.controller;

import com.nnk.springboot.dto.LoginDTO;
import com.nnk.springboot.security.MyUserDetailsService;
import com.nnk.springboot.security.jwt.JwtUtils;
import com.nnk.springboot.service.IUserService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private final IUserService userService;

    private JwtUtils jwtUtils;

    private AuthenticationManager auth;

    private MyUserDetailsService myUserDetailsService;

    @Autowired
    public LoginController(final IUserService userService, final JwtUtils jwtUtils, final AuthenticationManager auth,
                           final MyUserDetailsService myUserDetailsService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.auth = auth;
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping("/login")
    public String login(final Model model) {
        LOGGER.debug("GET Request on /login");

        model.addAttribute("loginDTO", new LoginDTO());

        LOGGER.info("GET Request on /login - SUCCESS");
        return "/login";
    }

    @PostMapping("/signin")
    public String authenticateUser(@Valid final LoginDTO loginRequest, final BindingResult result,
                                   final HttpServletResponse response) {
        LOGGER.debug("GET Request on /signin");

        if (result.hasErrors()) {
            return "/login";
        }

        Authentication authentication = auth.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);

        Cookie cookie = new Cookie("Token", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        LOGGER.info("GET Request on /signin - SUCCESS");
        return "redirect:/bidList/list";
    }

    @GetMapping("/403")
    public String error() {
        LOGGER.debug("GET Request on /403");

        return "/403";
    }
}
