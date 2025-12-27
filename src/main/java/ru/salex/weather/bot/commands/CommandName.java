package ru.salex.weather.bot.commands;

import lombok.Getter;

@Getter
public enum CommandName {
    ABOUT("about"),
    START("start"),
    WEATHER("weather"),
    UNKNOWN("unknown");


    private final String name;

    CommandName(String name) {
        this.name = name;
    }

}