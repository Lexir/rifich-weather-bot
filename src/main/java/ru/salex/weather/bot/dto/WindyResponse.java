package ru.salex.weather.bot.dto;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WindyResponse(
    @JsonAlias("ts") List<Long> timestamps,
    @JsonAlias("temp-surface") List<Double> tempSurface,
    @JsonAlias("wind_u-surface") List<Double> windU,
    @JsonAlias("wind_v-surface") List<Double> windV,
    @JsonAlias("rh-surface") List<Double> humidity
) {}