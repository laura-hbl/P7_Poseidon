package com.nnk.springboot.dto;

import javax.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class LoginDTO {

    @NotEmpty(message = "Type is mandatory")
    private String username;

    @NotEmpty(message = "Type is mandatory")
    private String password;

}
