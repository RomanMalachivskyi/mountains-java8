package com.home.education.mountains.resource;

public enum WeatherCondition {
    AUTUMN("autumn"),
    SPRING("spring"),
    SUMMER("summer"),
    WINTER("winter");

    private String value;

    WeatherCondition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
