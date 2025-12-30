package ru.salex.weather.bot.domain;

public record Precipitation(double mm) {

    public boolean isPresent() {
        return mm > 0;
    }
}
