package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.code.bluetick.enums.Destination;
import org.code.bluetick.persistence.model.Traveller;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelDetailDto {

    @JsonProperty("departureCity")
    @NotNull(message = "Departure City must be specified.")
    String departureCity;

    @NotNull(message = "Nationality must be specified.")
    String nationality;

    @JsonProperty("travelDate")
    @NotNull(message = "Travel Date must be specified.")
    @FutureOrPresent(message = "Travel date must be today or in future")
    LocalDate travelDate;

    @Min(value = 1, message = "Minimum 1 room should be booked")
    @Max(value = 10, message = "Maximum 10 rooms should be booked in a single booking")
    int rooms;

    @JsonProperty("totalNights")
    @Min(value = 1, message = "Minimum 1N/2D should be booked")
    @Max(value = 20, message = "Maximum 20N/21D should be booked in a single booking")
    int totalNights;

    @JsonProperty("destinations")
    Set<Destination> destinations;

    @JsonProperty("travellers")
    List<Traveller> travellers;
}
