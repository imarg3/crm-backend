package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.code.bluetick.enums.LeadStatus;

import java.time.LocalDate;

@Getter
@Setter
public class LeadDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("customer")
    private CustomerDto customer;

    @JsonProperty("createdDate")
    private LocalDate createdDate;

    @JsonProperty("travelDetail")
    private TravelDetailDto travelDetail;

    @JsonProperty("status")
    private LeadStatus status;
}
