package ru.salex.weather.bot.domain;

public enum Level {

    H500("500 м"),
    H1000("1000 м"),
    H1500("1500 м"),
    H2000("2000 м"),
    H3000("3000 м");

    private final String label;

    Level(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}

