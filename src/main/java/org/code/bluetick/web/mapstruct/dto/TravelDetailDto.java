package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.code.bluetick.enums.Destination;

import java.time.LocalDate;

@Getter
@Setter
public class TravelDetailDto {
    @JsonProperty("destination")
    private Destination destination;

    @JsonProperty("departureCity")
    private String departureCity;

    @JsonProperty("travelDate")
    private LocalDate travelDate;

    @JsonProperty("totalNights")
    private int totalNights;
}
