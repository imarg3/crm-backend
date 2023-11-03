package org.code.bluetick.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.persistence.model.Lead;
import org.code.bluetick.web.mapstruct.dto.LeadDto;
import org.code.bluetick.web.mapstruct.mapper.MapStructMapper;
import org.code.bluetick.service.LeadServiceImpl;
import org.code.bluetick.web.payload.GenericResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/lead")
@Slf4j
@AllArgsConstructor
public class LeadController {
    private final MapStructMapper mapStructMapper;

    private final LeadServiceImpl leadService;

    @GetMapping("/all")
    public ResponseEntity<List<Lead>> getAllCustomers(Pageable pageable) {
        Page<Lead> page = leadService.getAllLeads(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> createLead(@Valid @RequestBody final LeadDto leadDto) {
        log.info("Creating new lead with information: {}",  leadDto);

        final Lead lead = leadService.createNewLead(mapStructMapper.leadDtoToLead(leadDto));
        System.out.println("Lead saved :: " + lead.toString());

        return ResponseEntity.status(201).body(GenericResponse.builder()
                .message("New Lead is successfully created")
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<GenericResponse> findLeadByLeadId(@PathVariable String leadId) {
        Lead lead = leadService.findLeadByLeadId(leadId);
        return ResponseEntity.status(200).body(GenericResponse.builder()
                .message("Lead found: " + lead)
                .status(HttpStatus.OK)
                .build());
    }
}
