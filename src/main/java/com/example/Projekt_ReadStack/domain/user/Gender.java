package com.example.Projekt_ReadStack.domain.user;

public enum Gender {
    MALE("Mężczyzna"), FEMALE("Kobieta");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    public static Gender fromName(String name) {
        for (Gender gender : Gender.values()) {
            if (gender.getName().equals(name)) {
                return gender;
            }
        }
        return null;
    }

    public String getName() {
        if (this == null) {
            return null;
        }
        return name;
    }
}
