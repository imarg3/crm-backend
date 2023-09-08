package org.code.bluetick.web.mapstruct.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Please provide User email address")
    private String usernameOrEmail;

    @NotNull(message = "Please provide User password")
    private String password;
}
