package org.code.bluetick.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.enums.LeadStatus;
import org.code.bluetick.persistence.model.Customer;
import org.code.bluetick.persistence.model.Lead;
import org.code.bluetick.persistence.model.TravelDetail;
import org.code.bluetick.persistence.model.Traveller;
import org.code.bluetick.persistence.repository.LeadRepository;
import org.code.bluetick.utils.LeadUtils;
import org.code.bluetick.web.exception.LeadAlreadyExistException;
import org.code.bluetick.web.exception.LeadNotFoundException;
import org.code.bluetick.web.mapstruct.dto.LeadDto;
import org.code.bluetick.web.mapstruct.dto.LeadResponseDto;
import org.code.bluetick.web.mapstruct.mapper.MapStructMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LeadServiceImpl implements LeadService {
    private final LeadRepository leadRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    public LeadResponseDto createNewLead(final LeadDto leadDto) {
        log.info("Creating new lead for customer email: {}", leadDto.getCustomer().getEmail());
        Assert.notNull(leadDto, "Lead must not be null");
        
        Lead lead = mapStructMapper.leadDtoToLead(leadDto);
        
        // Generate a unique lead ID
        final String leadId = LeadUtils.generateLeadID();
        log.debug("Generated lead ID: {}", leadId);
        
        if (leadAlreadyExists(leadId)) {
            throw new LeadAlreadyExistException("There is already a lead with a lead id: " + leadId);
        }
        
        lead.setLeadId(leadId);
        lead.setStatus(LeadStatus.QUOTE_SENT);

        // Set up bi-directional relationships
        Customer customer = lead.getCustomer();
        TravelDetail travelDetail = lead.getTravelDetail();
        
        if (travelDetail.getTravellers() != null) {
            travelDetail.getTravellers().forEach(traveller -> traveller.setTravelDetail(travelDetail));
        }

        Lead savedLead = leadRepository.save(lead);
        log.info("Lead created successfully with ID: {}", savedLead.getLeadId());
        
        return mapStructMapper.leadToLeadResponseDto(savedLead);
    }

    @Override
    @Transactional(readOnly = true)
    public LeadResponseDto findLeadByLeadId(String leadId) {
        log.debug("Finding lead by lead ID: {}", leadId);
        Optional<Lead> optionalLead = leadRepository.findByLeadId(leadId);
        Lead lead = optionalLead.orElseThrow(() -> 
            new LeadNotFoundException("No lead found with lead id: " + leadId));
        return mapStructMapper.leadToLeadResponseDto(lead);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadResponseDto> getAllLeads(Pageable pageable) {
        log.debug("Retrieving all leads with pagination");
        Page<Lead> leadPage = leadRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                ));
        return mapStructMapper.leadListToLeadResponseDtoList(leadPage.getContent());
    }

    private boolean leadAlreadyExists(final String leadId) {
        return leadRepository.findByLeadId(leadId).isPresent();
    }
}