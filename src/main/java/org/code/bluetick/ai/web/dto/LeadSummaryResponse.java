package org.code.bluetick.ai.web.dto;

public record LeadSummaryResponse(
        String leadId,
        String summary,
        String keyInsights,
        String recommendedAction,
        String provider
) {}
