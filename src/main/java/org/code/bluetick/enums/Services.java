package org.code.bluetick.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Services {
    STAYS("Stays"), FLIGHTS("Flights"), TRANSFERS("Transfers"), SIGHTSEEING("Sightseeing"),
    CRUISE("Cruise"), VISA("Visa"), INSURANCE("Insurance"), FERRY("Ferry"), TRAIN("Train"), BUS("Bus");
    private final String service;

    Services(final String service) {
        this.service = service;
    }

    @JsonValue
    public String getService() {
        return service;
    }

    public static Services of(String serviceName) {
        return Stream.of(Services.values())
                .filter(service -> service.getService().equals(serviceName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

