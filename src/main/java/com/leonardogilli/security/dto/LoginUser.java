package com.leonardogilli.security.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginUser {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
