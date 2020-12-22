package com.nnk.springboot.integration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Tag("FindByUsername")
    public void givenARegisteredUsername_whenFindByUsername_thenReturnTheRegisteredUser() {
        User user = userRepository.findByUsername("admin");

        assertThat(user.getFullname()).isEqualTo("Administrator");
        assertThat(user.getId()).isEqualTo(1);
    }

    @Test
    @Tag("FindByUsername - Exception")
    public void givenAnUnRegisteredUsername_whenFindByUsername_thenReturnNull() {
        User user = userRepository.findByUsername("notRegistered");

        assertThat(user).isEqualTo(null);
    }
}
