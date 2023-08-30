/*
package org.code.bluetick.web.controller;

import org.code.bluetick.web.mapstruct.dto.LeadDto;
import org.code.bluetick.web.mapstruct.mapper.MapStructMapper;
import org.code.bluetick.service.LeadService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/api/lead")
public class LeadController {
    private final MapStructMapper mapStructMapper;

    private final LeadService leadService;

    public LeadController(MapStructMapper mapStructMapper, LeadService leadService) {
        this.mapStructMapper = mapStructMapper;
        this.leadService = leadService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LeadDto>> getAllCustomers(Pageable pageable) {
        List<Lead> leadsList = leadService.getAllLeads(pageable).getContent();
        return ResponseEntity.ok(mapStructMapper.leadListToLeadDtoList(leadsList));
    }
}
*/
