package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {

    @NotNull(message = "Please provide Customer Name")
    @Size(min = 2, max = 50, message = "Customer full Name must be between 2 to 50 characters")
    String name;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Please provide Customer email address")
    String email;

    @NotNull(message = "Please provide User mobile number")
    @Size(max = 13)
    String mobile;

    @JsonProperty("birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide Customer date of birth in DD-MM-YYYY format")
    LocalDate birthDate;
}
