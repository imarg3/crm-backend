package org.code.bluetick.enums;

import java.util.stream.Stream;

public enum PersonType {
    ADULT("Adult"), CHILD("Child");

    private final String type;

    PersonType(final String type) {
        this.type = type;
    }

    public String getPersonType() {
        return type;
    }

    public static PersonType of(String type) {
        return Stream.of(PersonType.values())
                .filter(personType -> personType.getPersonType().equals( type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
