package org.code.bluetick.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.web.mapstruct.dto.LeadDto;
import org.code.bluetick.web.mapstruct.dto.LeadResponseDto;
import org.code.bluetick.service.LeadServiceImpl;
import org.code.bluetick.web.payload.GenericResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/leads")
@Slf4j
@AllArgsConstructor
public class LeadController {
    private final LeadServiceImpl leadService;

    @GetMapping
    public ResponseEntity<GenericResponse<List<LeadResponseDto>>> getAllLeads(Pageable pageable) {
        List<LeadResponseDto> leads = leadService.getAllLeads(pageable);
        return ResponseEntity.ok(GenericResponse.success("Leads retrieved successfully", leads));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<LeadResponseDto>> createLead(@Valid @RequestBody final LeadDto leadDto) {
        log.info("Creating new lead with customer email: {}", leadDto.getCustomer().getEmail());

        final LeadResponseDto lead = leadService.createNewLead(leadDto);
        log.info("Lead created successfully with ID: {}", lead.getLeadId());

        return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse
                .success("Lead created successfully", lead));
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<GenericResponse<LeadResponseDto>> findLeadByLeadId(@PathVariable String leadId) {
        LeadResponseDto lead = leadService.findLeadByLeadId(leadId);
        return ResponseEntity.ok(GenericResponse.success("Lead found", lead));
    }
}
