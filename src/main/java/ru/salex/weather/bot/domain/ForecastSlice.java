package ru.salex.weather.bot.domain;

import ru.salex.weather.bot.dto.WindyResponse;

public final class ForecastSlice {

    private final String label;
    private final TemperatureProfile temperature;
    private final HumidityProfile humidity;
    private final Wind wind;
    private final Precipitation precipitation;

    private ForecastSlice(
            String label,
            TemperatureProfile temperature,
            HumidityProfile humidity,
            Wind wind,
            Precipitation precipitation
    ) {
        this.label = label;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.precipitation = precipitation;
    }

    public static ForecastSlice from(
            WindyResponse r,
            ForecastOffset offset
    ) {
        int i = offset.index();
        return new ForecastSlice(
                offset.label(),
                TemperatureProfile.from(r, i),
                HumidityProfile.from(r, i),
                extractWind(r, i),
                extractPrecip(r, i)
        );
    }

    public String label() {
        return label;
    }

    public TemperatureProfile temperature() {
        return temperature;
    }

    public HumidityProfile humidity() {
        return humidity;
    }

    public Wind wind() {
        return wind;
    }

    public Precipitation precipitation() {
        return precipitation;
    }

    private static Wind extractWind(WindyResponse r, int i) {
        if (r.windUSurface() == null || i >= r.windUSurface().size()) {
            return null;
        }
        return Wind.fromUV(
                r.windUSurface().get(i),
                r.windVSurface().get(i)
        );
    }

    private static Precipitation extractPrecip(WindyResponse r, int i) {
        if (r.precip() == null || i >= r.precip().size()) {
            return new Precipitation(0);
        }
        return new Precipitation(r.precip().get(i));
    }
}
