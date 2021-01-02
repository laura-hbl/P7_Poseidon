package com.nnk.springboot.domain;

import javax.persistence.*;
import lombok.*;

/**
 * Permits the storage and retrieving data of an user.
 *
 * @author Laura Habdul
 */
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class User {

    /**
     * Id of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Password of the user.
     */
    private String password;

    /**
     * Full name of the user.
     */
    private String fullname;

    /**
     * Role of the user.
     */
    private String role;

    /**
     * Constructor of class User.
     * Initialize username, password, fullname and role.
     *
     * @param username username of the user.
     * @param password password of the user
     * @param fullname full name of the user
     * @param role     role of the user
     */
    public User(final String username, final String password, final String fullname, final String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }
}