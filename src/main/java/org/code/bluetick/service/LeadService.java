package org.code.bluetick.service;

import org.code.bluetick.web.mapstruct.dto.LeadDto;
import org.code.bluetick.web.mapstruct.dto.LeadResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LeadService {
    LeadResponseDto createNewLead(LeadDto leadDto);

    LeadResponseDto findLeadByLeadId(String leadId);

    List<LeadResponseDto> getAllLeads(Pageable pageable);
}
