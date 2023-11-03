package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.code.bluetick.enums.PersonType;

@Data
public class TravellerDto {
    @JsonProperty("name")
    @NotNull(message = "Traveller name must be specified.")
    private String name;

    @JsonProperty("age")
    @NotNull(message = "Traveller age must be specified.")
    private short age;

    @JsonProperty("personType")
    @NotNull(message = "Person type must be specified.")
    private PersonType personType;
}

