package com.nnk.springboot.repository;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository interface that provides methods that permit interaction with database users table.
 *
 * @author Laura Habdul
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds an user by the username.
     *
     * @param username username of the user
     * @return The user found
     */
    User findByUsername(String username);
}
