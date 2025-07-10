package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.code.bluetick.enums.LeadStatus;
import org.code.bluetick.enums.Services;

import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeadResponseDto {
    private Long id;
    private String leadId;
    private CustomerResponseDto customer;
    private TravelDetailResponseDto travelDetail;
    private LeadStatus status;
    private Services[] services;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant createdAt;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant updatedAt;
} 