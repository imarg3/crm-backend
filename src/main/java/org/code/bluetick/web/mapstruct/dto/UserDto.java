package org.code.bluetick.web.mapstruct.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.code.bluetick.validation.PasswordMatches;
import org.code.bluetick.validation.ValidEmail;
import org.code.bluetick.validation.ValidPassword;

@PasswordMatches
@Data
public class UserDto {
    @NotNull
    @Size(min = 1, max = 50, message = "{Size.userDto.firstName}")
    private String fullName;

    @NotNull
    @Size(min = 1, max = 50, message = "{Size.userDto.lastName}")
    private String businessName;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 8)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    @NotNull
    @Size(max = 13)
    private String mobile;

    @NotNull
    @Size(min = 3, max = 30, message = "Please provide country name (3 to 30 characters")
    private String country;

    @NotNull
    @Size(min = 3, max = 30, message = "Please provide state name (3 to 30 characters")
    private String state;

    @NotNull
    @Size(min = 3, max = 30, message = "Please provide city name (3 to 30 characters")
    private String city;
}
