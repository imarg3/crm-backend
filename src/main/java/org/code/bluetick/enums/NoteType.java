package org.code.bluetick.enums;

/**
 * Types of notes that can be added to a lead.
 * Helps categorize communication and interaction types in the CRM.
 */
public enum NoteType {
    CALL("Phone Call"),
    EMAIL("Email Communication"),
    MEETING("Meeting"),
    GENERAL("General Note"),
    FOLLOW_UP("Follow-up Required"),
    QUOTATION("Quotation Sent"),
    CUSTOMER_FEEDBACK("Customer Feedback");

    private final String displayName;

    NoteType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
