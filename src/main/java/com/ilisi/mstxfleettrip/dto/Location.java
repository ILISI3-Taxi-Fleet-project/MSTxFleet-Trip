package com.ilisi.mstxfleettrip.dto;

public record Location(
        double latitude,
        double longitude
) {
    public String toWktPoint() {
        return String.format("POINT (%s %s)", longitude, latitude);
    }
}