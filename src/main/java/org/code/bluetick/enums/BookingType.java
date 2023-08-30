package org.code.bluetick.enums;

import java.util.stream.Stream;

public enum BookingType {
    PACKAGE("Package"), HOTEL("Hotel");

    private final String type;

    BookingType(final String type) {
        this.type = type;
    }

    public String getBookingType() {
        return type;
    }

    public static BookingType of(String type) {
        return Stream.of(BookingType.values())
                .filter(bookingType -> bookingType.getBookingType().equals( type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
