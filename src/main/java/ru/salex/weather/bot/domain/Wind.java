package ru.salex.weather.bot.domain;

public record Wind(double speed, double direction) {

    public static Wind fromUV(double u, double v) {
        double speed = Math.sqrt(u * u + v * v);
        double direction = (Math.toDegrees(Math.atan2(u, v)) + 360) % 360;
        return new Wind(speed, direction);
    }
}

