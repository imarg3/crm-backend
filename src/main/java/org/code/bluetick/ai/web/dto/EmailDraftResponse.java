package org.code.bluetick.ai.web.dto;

public record EmailDraftResponse(
        String leadId,
        String subject,
        String body,
        String provider
) {}
