package com.safetytracker.api.model;

public class BREntity {
    private String value;
    private String label;

    public BREntity(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return "BR " + label;
    }
}
