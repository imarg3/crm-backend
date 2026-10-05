package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.code.bluetick.enums.PersonType;

/**
 * Traveller DTO using Java record for immutability.
 * Represents a single traveler in a booking.
 */
public record TravellerDto(
    @JsonProperty("name")
    @NotNull(message = "Traveller name must be specified.")
    String name,

    @JsonProperty("age")
    @NotNull(message = "Traveller age must be specified.")
    short age,

    @JsonProperty("personType")
    @NotNull(message = "Person type must be specified.")
    PersonType personType
) {}
