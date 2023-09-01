package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.code.bluetick.validation.PasswordMatches;
import org.code.bluetick.validation.ValidPassword;

@PasswordMatches
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @NotNull(message = "Please provide User full name")
    @Size(min = 2, max = 50, message = "User full Name must be between 2 to 50 characters")
    private String fullName;

    @NotNull(message = "Please provide User business name")
    @Size(min = 2, max = 50, message = "Business Name must be between 2 to 50 characters")
    private String businessName;

    @NotNull(message = "Please provide User password")
    @ValidPassword
    private String password;

    @NotNull(message = "Please provide User matching password")
    @Size(min = 8)
    private String matchingPassword;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Please provide User email address")
    private String email;

    @NotNull(message = "Please provide User mobile number")
    @Size(max = 13)
    private String mobile;

    @NotNull(message = "Please provide User country name")
    @Size(min = 3, max = 30, message = "Please provide country name (3 to 30 characters")
    private String country;

    @NotNull(message = "Please provide User state name")
    @Size(min = 3, max = 30, message = "Please provide state name (3 to 30 characters")
    private String state;

    @NotNull(message = "Please provide User city name")
    @Size(min = 3, max = 30, message = "Please provide city name (3 to 30 characters")
    private String city;
}
