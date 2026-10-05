package org.code.bluetick.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.ai.provider.AIModelProvider;
import org.code.bluetick.ai.web.dto.EmailDraftResponse;
import org.code.bluetick.ai.web.dto.ItineraryDay;
import org.code.bluetick.ai.web.dto.ItineraryResponse;
import org.code.bluetick.ai.web.dto.LeadSummaryResponse;
import org.code.bluetick.persistence.model.Lead;
import org.code.bluetick.persistence.model.LeadActivity;
import org.code.bluetick.persistence.model.LeadNote;
import org.code.bluetick.persistence.repository.LeadActivityRepository;
import org.code.bluetick.persistence.repository.LeadNoteRepository;
import org.code.bluetick.persistence.repository.LeadRepository;
import org.code.bluetick.web.exception.LeadNotFoundException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "feature.ai.enabled", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class LeadAIServiceImpl implements LeadAIService {

    private static final String SUMMARY_SYSTEM = """
            You are a CRM assistant for a travel company. Analyze the lead and provide a structured response.
            Be concise and actionable. Format your response exactly as:
            SUMMARY: [2-3 sentence overview]
            INSIGHTS: [key observations about this customer's intent and readiness]
            ACTION: [single most important next step for the sales agent]
            """;

    private static final String EMAIL_SYSTEM = """
            You are a friendly, professional travel sales agent. Write personalized follow-up emails
            that feel warm, not templated. Keep it under 150 words. Format exactly as:
            SUBJECT: [compelling subject line]
            BODY: [email body]
            """;

    private static final String ITINERARY_SYSTEM = """
            You are an expert travel planner. Create detailed day-by-day itineraries.
            Respond ONLY with valid JSON matching this schema exactly — no markdown, no extra text:
            {"days":[{"day":1,"title":"string","activities":["string"],"accommodation":"string","meals":"string"}],
             "includes":["string"],"excludes":["string"]}
            """;

    private final AIModelProvider aiModelProvider;
    private final LeadRepository leadRepository;
    private final LeadNoteRepository leadNoteRepository;
    private final LeadActivityRepository leadActivityRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public LeadSummaryResponse summarizeLead(String leadId) {
        Lead lead = findLead(leadId);
        String context = buildLeadContext(lead);

        String raw = aiModelProvider.generate(SUMMARY_SYSTEM, context);
        return parseLeadSummary(leadId, raw);
    }

    @Override
    @Transactional(readOnly = true)
    public EmailDraftResponse draftFollowUpEmail(String leadId) {
        Lead lead = findLead(leadId);
        String context = buildEmailContext(lead);

        String raw = aiModelProvider.generate(EMAIL_SYSTEM, context);
        return parseEmailDraft(leadId, raw);
    }

    @Override
    @Transactional(readOnly = true)
    public ItineraryResponse generateItinerary(String leadId) {
        Lead lead = findLead(leadId);
        String context = buildItineraryContext(lead);

        String raw = aiModelProvider.generate(ITINERARY_SYSTEM, context);
        String destinationLabel = Arrays.stream(lead.getTravelDetail().getDestinations())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        return parseItinerary(leadId, destinationLabel, lead.getTravelDetail().getTotalNights(), raw);
    }

    private Lead findLead(String leadId) {
        return leadRepository.findByLeadId(leadId)
                .orElseThrow(() -> new LeadNotFoundException("Lead not found: " + leadId));
    }

    private String buildLeadContext(Lead lead) {
        List<LeadNote> notes = leadNoteRepository.findByLeadOrderByCreatedAtDesc(lead);
        List<LeadActivity> activities = leadActivityRepository.findByLeadOrderByCreatedAtDesc(lead);

        String noteSummary = notes.isEmpty() ? "No notes recorded." :
                notes.stream().limit(5)
                        .map(n -> "[%s] %s: %s".formatted(n.getNoteType(), n.getCreatedAt(), n.getContent()))
                        .collect(Collectors.joining("\n"));

        String activitySummary = activities.isEmpty() ? "No activities recorded." :
                activities.stream().limit(5)
                        .map(a -> "[%s] %s".formatted(a.getActivityType(), a.getDescription()))
                        .collect(Collectors.joining("\n"));

        return """
                Lead: %s
                Customer: %s (%s)
                Destinations: %s
                Departure: %s
                Travel Date: %s
                Nights: %d
                Status: %s
                Services: %s

                Recent Notes:
                %s

                Recent Activities:
                %s
                """.formatted(
                lead.getLeadId(),
                lead.getCustomer().getName(), lead.getCustomer().getEmail(),
                Arrays.toString(lead.getTravelDetail().getDestinations()),
                lead.getTravelDetail().getDepartureCity(),
                lead.getTravelDetail().getTravelDate(),
                lead.getTravelDetail().getTotalNights(),
                lead.getStatus(),
                Arrays.toString(lead.getServices()),
                noteSummary,
                activitySummary
        );
    }

    private String buildEmailContext(Lead lead) {
        return """
                Customer name: %s
                Destination(s): %s
                Departure city: %s
                Travel date: %s
                Duration: %d nights
                Lead status: %s
                Services requested: %s
                """.formatted(
                lead.getCustomer().getName(),
                Arrays.toString(lead.getTravelDetail().getDestinations()),
                lead.getTravelDetail().getDepartureCity(),
                lead.getTravelDetail().getTravelDate(),
                lead.getTravelDetail().getTotalNights(),
                lead.getStatus(),
                Arrays.toString(lead.getServices())
        );
    }

    private String buildItineraryContext(Lead lead) {
        int travellers = lead.getTravelDetail().getTravellers() == null ? 2
                : lead.getTravelDetail().getTravellers().size();
        return """
                Destination(s): %s
                Departure city: %s
                Travel date: %s
                Total nights: %d
                Number of travellers: %d
                Services: %s
                """.formatted(
                Arrays.toString(lead.getTravelDetail().getDestinations()),
                lead.getTravelDetail().getDepartureCity(),
                lead.getTravelDetail().getTravelDate(),
                lead.getTravelDetail().getTotalNights(),
                travellers,
                Arrays.toString(lead.getServices())
        );
    }

    private LeadSummaryResponse parseLeadSummary(String leadId, String raw) {
        String summary = extractSection(raw, "SUMMARY:", "INSIGHTS:");
        String insights = extractSection(raw, "INSIGHTS:", "ACTION:");
        String action = extractSection(raw, "ACTION:", null);
        return new LeadSummaryResponse(leadId, summary, insights, action, aiModelProvider.providerName());
    }

    private EmailDraftResponse parseEmailDraft(String leadId, String raw) {
        String subject = extractSection(raw, "SUBJECT:", "BODY:").trim();
        String body = extractSection(raw, "BODY:", null).trim();
        return new EmailDraftResponse(leadId, subject, body, aiModelProvider.providerName());
    }

    private ItineraryResponse parseItinerary(String leadId, String destination, int totalNights, String raw) {
        try {
            String json = raw.trim();
            if (json.startsWith("```")) {
                json = json.replaceAll("```json\\n?", "").replaceAll("```\\n?", "").trim();
            }
            JsonNode root = objectMapper.readTree(json);
            List<ItineraryDay> days = new ArrayList<>();
            for (JsonNode dayNode : root.path("days")) {
                List<String> activities = new ArrayList<>();
                dayNode.path("activities").forEach(a -> activities.add(a.asText()));
                days.add(new ItineraryDay(
                        dayNode.path("day").asInt(),
                        dayNode.path("title").asText(),
                        activities,
                        dayNode.path("accommodation").asText(),
                        dayNode.path("meals").asText()
                ));
            }
            List<String> includes = new ArrayList<>();
            root.path("includes").forEach(i -> includes.add(i.asText()));
            List<String> excludes = new ArrayList<>();
            root.path("excludes").forEach(e -> excludes.add(e.asText()));
            return new ItineraryResponse(leadId, destination, totalNights, days, includes, excludes, aiModelProvider.providerName());
        } catch (Exception e) {
            log.warn("Failed to parse itinerary JSON, returning raw fallback for lead {}", leadId, e);
            return new ItineraryResponse(leadId, destination, totalNights,
                    List.of(new ItineraryDay(1, "Itinerary", List.of(raw), "", "")),
                    List.of(), List.of(), aiModelProvider.providerName());
        }
    }

    private String extractSection(String text, String startMarker, String endMarker) {
        int start = text.indexOf(startMarker);
        if (start == -1) return text.trim();
        start += startMarker.length();
        if (endMarker == null) return text.substring(start).trim();
        int end = text.indexOf(endMarker, start);
        return end == -1 ? text.substring(start).trim() : text.substring(start, end).trim();
    }
}
