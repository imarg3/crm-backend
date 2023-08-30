package org.code.bluetick.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Destination {
    DUBAI("Dubai"), SINGAPORE("Singapore"), MALAYSIA("Malaysia"), THAILAND("Thailand"), BALI("Bali");
    private final String destination;

    Destination(final String destination) {
        this.destination = destination;
    }

    @JsonValue
    public String getDestination() {
        return destination;
    }

    public static Destination of(String destinationName) {
        return Stream.of(Destination.values())
                .filter(destination -> destination.getDestination().equals(destinationName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
