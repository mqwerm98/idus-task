package com.idus.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum Gender {
    M, F;

    @JsonCreator
    public static Gender create(String request) {
        return Stream.of(values())
                .filter(v -> v.toString().equalsIgnoreCase(request))
                .findFirst()
                .orElse(null);
    }
}
