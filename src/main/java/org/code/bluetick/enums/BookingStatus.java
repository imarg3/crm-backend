package org.code.bluetick.enums;

import java.util.stream.Stream;

public enum BookingStatus {
    CONFIRMED("Confirmed"), HOLD("Hold");

    private final String status;

    private BookingStatus(final String status) {
        this.status = status;
    }

    public String getBookingStatus() {
        return status;
    }

    public static BookingStatus of(String status) {
        return Stream.of(BookingStatus.values())
                .filter((bookingStatus -> bookingStatus.getBookingStatus().equals(status)))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
