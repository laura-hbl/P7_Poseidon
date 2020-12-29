package com.nnk.springboot.security.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Custom AuthTokenFilter class that extends OncePerRequestFilter and override doFilterInternal() method.
 * Filter that executes once per request.
 *
 * @author Laura Habdul
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    /**
     * AuthTokenFilter logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AuthTokenFilter.class);

    /**
     * JwtUtils instance.
     */
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * UserDetailsService instance.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Gets JWT from the request cookie, if the request has JWT, validate it and parse username from it. Then from
     * username, get UserDetails to create an Authentication object and set the current UserDetails in SecurityContext
     * using setAuthentication(authentication) method.
     *
     * @param request     HttpServletRequest reference
     * @param response    HttpServletResponse reference
     * @param filterChain FilterChain reference
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {
        LOGGER.debug("Inside AuthTokenFilter's doFilterInternal method");

        try {
            // Gets the JWT from the request cookie
            Cookie[] cookies = request.getCookies();
            String jwt = cookies[0].getValue();

            // Validates the JWT if not null
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Parses username from the JWT
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // Gets UserDetails to create an Authentication object
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Sets the current UserDetails in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            LOGGER.error("Cannot set user authentication", e);
        }

        filterChain.doFilter(request, response);
    }
}
