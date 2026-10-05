package org.code.bluetick.ai.service;

import org.code.bluetick.ai.web.dto.EmailDraftResponse;
import org.code.bluetick.ai.web.dto.ItineraryResponse;
import org.code.bluetick.ai.web.dto.LeadSummaryResponse;

public interface LeadAIService {

    LeadSummaryResponse summarizeLead(String leadId);

    EmailDraftResponse draftFollowUpEmail(String leadId);

    ItineraryResponse generateItinerary(String leadId);
}
