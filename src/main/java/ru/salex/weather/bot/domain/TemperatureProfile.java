package ru.salex.weather.bot.domain;

import ru.salex.weather.bot.dto.WindyResponse;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class TemperatureProfile {

    private final Map<Level, Double> values;

    private TemperatureProfile(Map<Level, Double> values) {
        this.values = Map.copyOf(values);
    }

    public static TemperatureProfile from(
            WindyResponse r,
            int index
    ) {
        Map<Level, Double> map = new EnumMap<>(Level.class);

        put(map, Level.H500, r.temp950(), index);
        put(map, Level.H1000, r.temp900(), index);
        put(map, Level.H1500, r.temp850(), index);
        put(map, Level.H2000, r.temp800(), index);
        put(map, Level.H3000, r.temp700(), index);

        return new TemperatureProfile(map);
    }

    public Optional<Double> at(Level level) {
        return Optional.ofNullable(values.get(level));
    }

    private static void put(
            Map<Level, Double> map,
            Level level,
            List<Double> source,
            int i
    ) {
        if (source != null && i < source.size()) {
            map.put(level, source.get(i));
        }
    }
}
