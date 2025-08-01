package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.code.bluetick.enums.Destination;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TravelDetailResponseDto {
    private Long id;
    private String departureCity;
    private String nationality;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate travelDate;
    
    private short rooms;
    private short totalNights;
    private Destination[] destinations;
    private List<TravellerResponseDto> travellers;
} 