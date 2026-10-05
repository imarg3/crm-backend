package org.code.bluetick.ai.web.dto;

import java.util.List;

public record ItineraryDay(
        int day,
        String title,
        List<String> activities,
        String accommodation,
        String meals
) {}
