package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.code.bluetick.enums.Services;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeadDto {

    @JsonProperty("customer")
    @NotNull(message = "Please provide all the required customer details")
    CustomerDto customer;

    @JsonProperty("travelDetail")
    @NotNull(message = "Please provide all the required travel details")
    TravelDetailDto travelDetail;

    @JsonProperty("services")
    @NotNull(message = "Please provide all the required customer services")
    Services[] services;
}
