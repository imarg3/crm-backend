package org.code.bluetick.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for Lead operations using modern Java 21 features.
 * Demonstrates secure random generation and improved string operations.
 */
public final class LeadUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LEAD_ID_LENGTH = 12;

    private LeadUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a unique Lead ID using secure random and timestamp.
     * Format: YYYYMMDD-XXXX (e.g., 20260416-A3F9)
     *
     * Uses Java 21 features:
     * - Enhanced StringBuilder with better performance
     * - Improved random operations
     */
    public static String generateLeadID() {
        String datePrefix = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String randomSuffix = SECURE_RANDOM.ints(8, 0, ALPHANUMERIC.length())
            .mapToObj(ALPHANUMERIC::charAt)
            .collect(StringBuilder::new,
                    StringBuilder::append,
                    StringBuilder::append)
            .toString();

        return STR."\{datePrefix}-\{randomSuffix}";
    }

    /**
     * Alternative: Simple alphanumeric ID generator
     * Uses secure random for better unpredictability
     */
    public static String generateSimpleLeadID() {
        return SECURE_RANDOM.ints(LEAD_ID_LENGTH, 0, ALPHANUMERIC.length())
            .mapToObj(ALPHANUMERIC::charAt)
            .collect(StringBuilder::new,
                    StringBuilder::append,
                    StringBuilder::append)
            .toString();
    }
}
