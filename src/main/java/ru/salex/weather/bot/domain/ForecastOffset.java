package ru.salex.weather.bot.domain;

import java.util.List;

public record ForecastOffset(String label, int index) {

    public static final List<ForecastOffset> FIXED = List.of(
            new ForecastOffset("+3ч", 1),
            new ForecastOffset("+6ч", 2),
            new ForecastOffset("+9ч", 3),
            new ForecastOffset("+12ч", 4),
            new ForecastOffset("+24ч", 8)
    );
}
