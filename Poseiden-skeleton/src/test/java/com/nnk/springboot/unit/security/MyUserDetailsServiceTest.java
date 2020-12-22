package com.nnk.springboot.unit.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.security.MyUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    private User user;

    @Before
    public void setUp() {
        user = new User(1, "Laura", "passwordA123&", "Laura", "USER");
    }

    @Test
    @Tag("LoadUserByUsername")
    @DisplayName("Given an user, when loadUserByUsername, then return correct user details")
    public void givenAnUser_whenLoadUserByUsername_thenUserDetailsShouldBeReturnedCorrectly() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("Laura");

        assertThat(userDetails.getUsername()).isEqualTo("Laura");
        assertThat(userDetails.getPassword()).isEqualTo("passwordA123&");
        assertThat(userDetails.getAuthorities()).toString().contains("USER");
        verify(userRepository).findByUsername(anyString());
    }

    @Test(expected = UsernameNotFoundException.class)
    @Tag("LoadUserByUsername")
    @DisplayName("If user is not registered, when loadUserByUsername, then throw UsernameNotFoundException")
    public void givenAnUnFoundUser_whenLoadUserByUsername_thenUsernameNotFoundExceptionIsThrown() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        myUserDetailsService.loadUserByUsername("Laura");
    }
}
