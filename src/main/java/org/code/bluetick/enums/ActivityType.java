package org.code.bluetick.enums;

/**
 * Types of activities tracked in the CRM system.
 * Provides comprehensive audit trail of lead lifecycle.
 */
public enum ActivityType {
    LEAD_CREATED("Lead Created"),
    STATUS_CHANGED("Status Changed"),
    ASSIGNED("Lead Assigned"),
    REASSIGNED("Lead Reassigned"),
    NOTE_ADDED("Note Added"),
    EMAIL_SENT("Email Sent"),
    CALL_MADE("Call Made"),
    MEETING_SCHEDULED("Meeting Scheduled"),
    QUOTATION_SENT("Quotation Sent"),
    CUSTOMER_UPDATED("Customer Information Updated"),
    TRAVEL_DETAILS_UPDATED("Travel Details Updated");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
