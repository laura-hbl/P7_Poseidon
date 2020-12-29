package com.nnk.springboot.dto;

import javax.validation.constraints.NotEmpty;
import lombok.*;

/**
 * Permits the storage and retrieving data of user login credentials.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class LoginDTO {

    /**
     * Username of the user.
     */
    @NotEmpty(message = "Username is mandatory")
    private String username;

    /**
     * Password of the user.
     */
    @NotEmpty(message = "Password is mandatory")
    private String password;
}
