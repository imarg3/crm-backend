package org.code.bluetick.ai.web.dto;

import java.util.List;

public record ItineraryResponse(
        String leadId,
        String destination,
        int totalNights,
        List<ItineraryDay> days,
        List<String> includes,
        List<String> excludes,
        String provider
) {}
