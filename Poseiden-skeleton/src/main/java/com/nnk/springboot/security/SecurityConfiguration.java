package com.nnk.springboot.security;

import com.nnk.springboot.security.jwt.AuthTokenFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

/**
 * Application security configuration class.
 *
 * @author Laura Habdul
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * SecurityConfiguration logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(SecurityConfiguration.class);

    /**
     * UserDetailsService instance.
     */
    UserDetailsService userDetailsService;

    /**
     * Constructor of class SecurityConfiguration.
     * Initialize userDetailsService.
     *
     * @param userDetailsService UserDetailsService instance
     */
    @Autowired
    public SecurityConfiguration(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Creates an instance of AuthTokenFilter.
     *
     * @return AuthTokenFilter instance
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Configures the AuthenticationManagerBuilder to use the password encoder and the implementation of
     * UserDetailsService for configuring DaoAuthenticationProvider.
     *
     * @param authenticationManagerBuilder AuthenticationManagerBuilder instance
     */
    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Overrides authenticationManagerBean() method so the authentication manager is available in app context.
     *
     * @return AuthenticationManager reference
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Creates an instance of BCryptPasswordEncoder in order to encrypt the password.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Allows configuring web based security for specific http requests.
     *
     * @param http HttpSecurity
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/bidList/**", "/rating/**", "/ruleName/**", "/trade/**", "/curvePoint/**")
                .authenticated()
                .antMatchers("/user/**", "/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and().logout().logoutSuccessHandler(this::logoutSuccessHandler).permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(this::authenticationEntryPointHandler)
                .and().exceptionHandling().accessDeniedHandler(this::accessDeniedHandler);

        // For each api the filter has been added with above http request condition
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Handles logout success.
     *
     * @param response       HttpServletResponse object
     * @param request        HttpServletRequest object
     * @param authentication Authentication object
     */
    private void logoutSuccessHandler(final HttpServletRequest request, final HttpServletResponse response,
                                      final Authentication authentication) throws IOException {
        LOGGER.debug("Inside SecurityConfiguration's logoutSuccessHandler method");

        // Retrieves the current cookie
        Cookie cookie = WebUtils.getCookie(request, "Token");
        if (cookie != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            // Invalidates the cookie
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            // Redirects to home page after logout
            response.sendRedirect(request.getContextPath() + "?logout");
        }
    }

    /**
     * Handles authenticationEntryPoint.
     *
     * @param response HttpServletResponse object
     * @param request  HttpServletRequest object
     * @param e        AuthenticationException object
     */
    private void authenticationEntryPointHandler(final HttpServletRequest request, final HttpServletResponse response,
                                                 final AuthenticationException e) throws IOException {
        LOGGER.error("Unauthorized error: {}", e.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        if (e.getMessage().contains("Bad credentials")) {
            // Redirects to login page with error message if bad credentials
            response.sendRedirect("/login?error");
        } else {
            // Redirects to home page with error message if user is not authenticated
            response.sendRedirect("/?error");
        }
    }

    /**
     * Handles access denied.
     *
     * @param response HttpServletResponse object
     * @param request  HttpServletRequest object
     * @param e        AccessDeniedException object
     */
    private void accessDeniedHandler(final HttpServletRequest request, final HttpServletResponse response,
                                     final AccessDeniedException e)
            throws IOException {
        LOGGER.error("Access denied error: {}", e.getMessage());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.sendRedirect("/403");
    }
}