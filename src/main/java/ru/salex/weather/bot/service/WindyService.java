package ru.salex.weather.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.salex.weather.bot.client.GeocodingClient;
import ru.salex.weather.bot.client.WindyClient;
import ru.salex.weather.bot.dto.GeocodingResponse;
import ru.salex.weather.bot.dto.WindyRequest;
import ru.salex.weather.bot.dto.WindyResponse;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WindyService {

    public static final double CALVIN_CONST = 273.15;
    private final GeocodingClient geocodingClient;
    private final WindyClient windyClient;

    @Value("${windy.api.key}")
    private String windyApiKey;

    public String getWeather(String cityName) {
        GeocodingResponse geoResponse;
        try {
            geoResponse = geocodingClient.searchCity(cityName, 1, "ru", "json");
        } catch (Exception e) {
            log.error("Ошибка сервиса геокодирования", e);
            return "Ошибка сервиса геокодирования: " + e.getMessage();
        }

        if (geoResponse == null || geoResponse.results() == null || geoResponse.results().isEmpty()) {
            return "Город не найден. Проверьте правильность написания.";
        }

        var location = geoResponse.results().getFirst();

        try {
            WindyRequest request = new WindyRequest(
                    location.latitude(),
                    location.longitude(),
                    "gfs",
                    List.of("temp", "wind", "rh"),
                    List.of("surface"),
                    windyApiKey
            );

            WindyResponse windyResponse = windyClient.getPointForecast(request);

            return formatMessage(location, windyResponse);
        } catch (Exception e) {
            log.error("Ошибка при получении данных от Windy", e);
            return "Ошибка при получении данных от Windy (проверьте API Key).";
        }
    }

    private String formatMessage(GeocodingResponse.Location loc, WindyResponse res) {
        if (res == null || res.tempSurface() == null) {
            return "Нет данных от Windy.";
        }
        double tempCelsius = res.tempSurface().getFirst() - CALVIN_CONST;
        double u = res.windU().getFirst();
        double v = res.windV().getFirst();
        double windSpeed = Math.sqrt(u * u + v * v);
        double humidity = res.humidity().getFirst();

        return """
                🌬 *Погода (Windy.com)*
                📍 %s (%s)
                
                🌡 Температура: %.1f°C
                💨 Ветер: %.1f м/с
                💧 Влажность: %.0f%%
                """.formatted(loc.name(), loc.country(), tempCelsius, windSpeed, humidity);
    }
}