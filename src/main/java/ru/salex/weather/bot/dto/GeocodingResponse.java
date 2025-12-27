package ru.salex.weather.bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingResponse(List<Location> results) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Location(String name, double latitude, double longitude, String country) {
    }
}