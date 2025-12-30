package ru.salex.weather.bot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WindyResponse(
        @JsonAlias("ts")
        List<Long> timestamps,

        @JsonAlias("wind_u-surface")
        List<Double> windUSurface,

        @JsonAlias("wind_v-surface")
        List<Double> windVSurface,

        @JsonAlias("precip")
        List<Double> precip,

        @JsonAlias("temp-950h") List<Double> temp950,
        @JsonAlias("temp-900h") List<Double> temp900,
        @JsonAlias("temp-850h") List<Double> temp850,
        @JsonAlias("temp-800h") List<Double> temp800,
        @JsonAlias("temp-700h") List<Double> temp700,

        @JsonAlias("rh-950h") List<Double> rh950,
        @JsonAlias("rh-900h") List<Double> rh900,
        @JsonAlias("rh-850h") List<Double> rh850,
        @JsonAlias("rh-800h") List<Double> rh800,
        @JsonAlias("rh-700h") List<Double> rh700
) {
}