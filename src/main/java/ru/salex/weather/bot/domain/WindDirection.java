package ru.salex.weather.bot.domain;

public final class WindDirection {

    private WindDirection() {}

    public static String arrow(double degrees) {
        return switch ((int) Math.round(degrees / 45) % 8) {
            case 0 -> "⬆️"; // N
            case 1 -> "↗️"; // NE
            case 2 -> "➡️"; // E
            case 3 -> "↘️"; // SE
            case 4 -> "⬇️"; // S
            case 5 -> "↙️"; // SW
            case 6 -> "⬅️"; // W
            case 7 -> "↖️"; // NW
            default -> "";
        };
    }
}
