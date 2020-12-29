package com.nnk.springboot.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Custom implementation of UserDetailsService interface.
 * Contains one method that permit to load UserDetails by username.
 *
 * @author Laura Habdul
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    /**
     * MyUserDetailsService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(MyUserDetailsService.class);

    /**
     * UserRepository instance.
     */
    private final UserRepository userRepository;

    /**
     * Constructor of class MyUserDetailsService.
     * Initialize userRepository.
     *
     * @param userRepository UserRepository instance
     */
    public MyUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds and loads details of an user based on the username.
     *
     * @param username the user's username
     * @return UserDetails instance
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) {
        LOGGER.debug("Inside MyUserDetailsService.loadUserByUsername for username : " + username);

        User user = userRepository.findByUsername(username);

        if (user == null) {
            LOGGER.error("Request - FAILED: Invalid username or password");
            throw new UsernameNotFoundException("Invalid username or password");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Arrays.asList(authority));
    }
}
