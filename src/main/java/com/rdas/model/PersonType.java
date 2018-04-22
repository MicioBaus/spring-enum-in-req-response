package com.rdas.model;

import lombok.Getter;

import java.util.Arrays;

/**
 * Created by rdas on 22/04/2018.
 */
@Getter
public enum PersonType {

    MALE("male"), FEMALE("female"), GENDER_NEUTRAL("gender-neutral");

    private String personSex;

    PersonType(String type) {
        personSex = type;
    }

    public static PersonType fromValue(String value) {
        for (PersonType category : values()) {
            if (category.personSex.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }
}
