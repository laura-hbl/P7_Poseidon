package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class LoginDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
