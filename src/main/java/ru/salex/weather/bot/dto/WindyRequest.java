package ru.salex.weather.bot.dto;

import java.util.List;

public record WindyRequest(
        double lat,
        double lon,
        String model,
        List<String> parameters,
        List<String> levels,
        String key
) {
}