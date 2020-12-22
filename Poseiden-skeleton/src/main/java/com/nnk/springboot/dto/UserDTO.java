package com.nnk.springboot.dto;

import com.nnk.springboot.constant.UserConstraints;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class UserDTO {

    private Integer id;

    @NotEmpty(message = "Username is mandatory")
    @Length(max = UserConstraints.USERNAME_MAX_SIZE, message = "The maximum length for username is 125 characters")
    private String username;

    @NotEmpty(message = "Password is mandatory")
    @Length(max = UserConstraints.PASSWORD_MAX_SIZE, message = "The maximum length for password is 125 characters")
    @Pattern(regexp = UserConstraints.PASSWORD_PATTERN, message = "The password must contain at least 8 characters, " +
            "one uppercase letter, one number and one symbol")
    private String password;

    @NotEmpty(message = "Fullname is mandatory")
    @Length(max = UserConstraints.FULL_NAME_MAX_SIZE, message = "The maximum length for full name is 125 characters")
    private String fullname;

    @NotEmpty(message = "Role is mandatory")
    private String role;

    public UserDTO(final String username, final String password, final String fullname, final  String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }
}
