package com.safetytracker.api.model;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Locale;

public class CountieEntity {
    private String value;
    private String label;

    public CountieEntity(String value) {
        this.value = value;
        this.label = getLabel();
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(value.split("_"))
                .forEach(word -> builder.append((word.charAt(0) + "").toUpperCase(Locale.ROOT)).append(word.substring(1)).append(" "));
        return builder.toString().trim();
    }
}
