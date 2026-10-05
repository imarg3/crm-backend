package org.code.bluetick.web.mapstruct.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Login request DTO using Java record for immutability.
 * Records provide automatic equals(), hashCode(), toString(), and getters.
 */
public record LoginDto(
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Please provide User email address")
    String usernameOrEmail,

    @NotNull(message = "Please provide User password")
    String password
) {}
