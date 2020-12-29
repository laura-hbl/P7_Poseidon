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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Creates endpoints for login to the app and display error page.
 *
 * @author Laura Habdul
 * @see IUserService
 */
@Controller
public class LoginController {

    /**
     * LoginController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    /**
     * Cookie expiration time in seconds.
     */
    @Value("${poseidon.app.cookieExpirationSec}")
    private int cookieExpirationSec;

    /**
     * IUserService's implement class reference.
     */
    private final IUserService userService;

    /**
     * JwtUtils instance.
     */
    private JwtUtils jwtUtils;

    /**
     * AuthenticationManager's implement class reference.
     */
    private AuthenticationManager auth;

    /**
     * MyUserDetailsService instance.
     */
    private MyUserDetailsService myUserDetailsService;

    /**
     * Constructor of class CurvePointController.
     * Initialize curvePointService.
     *
     * @param userService          IUserService's implement class reference.
     * @param jwtUtils             IUserService's implement class reference.
     * @param auth                 IUserService's implement class reference.
     * @param myUserDetailsService MyUserDetailsService instance.
     */
    @Autowired
    public LoginController(final IUserService userService, final JwtUtils jwtUtils, final AuthenticationManager auth,
                           final MyUserDetailsService myUserDetailsService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.auth = auth;
        this.myUserDetailsService = myUserDetailsService;
    }

    /**
     * Displays login form.
     *
     * @param model makes a new LoginDTO object available to login HTML page
     * @return The reference to the login HTML page
     */
    @GetMapping("/login")
    public String showLoginForm(final Model model) {
        LOGGER.debug("GET Request on /login");

        model.addAttribute("loginDTO", new LoginDTO());

        LOGGER.info("GET Request on /login - SUCCESS");
        return "/login";
    }

    /**
     * Authenticates an user and provides a Token.
     *
     * @param loginDTO user's credentials
     * @param result   holds the result of validation and binding and contains errors that may have occurred
     * @param response HttpServletResponse instance
     * @return The reference to the login HTML page if result has no errors. Else, redirects to /bidList/list endpoint
     */
    @PostMapping("/signin")
    public String authenticateUser(@Valid final LoginDTO loginDTO, final BindingResult result,
                                   final HttpServletResponse response) {
        LOGGER.debug("GET Request on /signin");

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "/login";
        }

        Authentication authentication = auth.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);

        Cookie cookie = new Cookie("Token", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cookieExpirationSec);
        response.addCookie(cookie);

        LOGGER.info("GET Request on /signin - SUCCESS");
        return "redirect:/bidList/list";
    }

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
}
