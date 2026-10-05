package org.code.bluetick.ai.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.ai.service.LeadAIService;
import org.code.bluetick.ai.web.dto.EmailDraftResponse;
import org.code.bluetick.ai.web.dto.ItineraryResponse;
import org.code.bluetick.ai.web.dto.LeadSummaryResponse;
import org.code.bluetick.web.payload.GenericResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@ConditionalOnProperty(name = "feature.ai.enabled", havingValue = "true")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class AIController {

    private final LeadAIService leadAIService;

    @PostMapping("/leads/{leadId}/summary")
    public ResponseEntity<GenericResponse<LeadSummaryResponse>> summarizeLead(
            @PathVariable String leadId) {
        log.info("AI: summarizing lead {}", leadId);
        return ResponseEntity.ok(GenericResponse.success("Lead summary generated",
                leadAIService.summarizeLead(leadId)));
    }

    @PostMapping("/leads/{leadId}/email-draft")
    public ResponseEntity<GenericResponse<EmailDraftResponse>> draftEmail(
            @PathVariable String leadId) {
        log.info("AI: drafting email for lead {}", leadId);
        return ResponseEntity.ok(GenericResponse.success("Email draft generated",
                leadAIService.draftFollowUpEmail(leadId)));
    }

    @PostMapping("/leads/{leadId}/itinerary")
    public ResponseEntity<GenericResponse<ItineraryResponse>> generateItinerary(
            @PathVariable String leadId) {
        log.info("AI: generating itinerary for lead {}", leadId);
        return ResponseEntity.ok(GenericResponse.success("Itinerary generated",
                leadAIService.generateItinerary(leadId)));
    }
}
