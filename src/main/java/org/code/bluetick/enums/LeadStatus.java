package org.code.bluetick.enums;

import java.util.stream.Stream;

public enum LeadStatus {
    QUOTE_SENT("Quote_Sent"), QUOTE_EXPIRED("Quote_Expired");

    private final String status;

    private LeadStatus(final String status) {
        this.status = status;
    }

    public String getLeadStatus() {
        return status;
    }

    public static LeadStatus of(String status) {
        return Stream.of(LeadStatus.values())
                .filter((leadStatus -> leadStatus.getLeadStatus().equals(status)))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
