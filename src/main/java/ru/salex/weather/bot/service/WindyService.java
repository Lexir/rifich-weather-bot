package ru.salex.weather.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.salex.weather.bot.client.GeocodingClient;
import ru.salex.weather.bot.client.WindyClient;
import ru.salex.weather.bot.domain.ForecastOffset;
import ru.salex.weather.bot.domain.ForecastSlice;
import ru.salex.weather.bot.dto.GeocodingResponse;
import ru.salex.weather.bot.dto.MessageView;
import ru.salex.weather.bot.dto.WindyRequest;
import ru.salex.weather.bot.dto.WindyResponse;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WindyService {
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
            return "❌ Ошибка сервиса геокодирования";
        }

        if (geoResponse == null || geoResponse.results() == null || geoResponse.results().isEmpty()) {
            return "❌ Город не найден";
        }

        var location = geoResponse.results().getFirst();

        try {
            WindyRequest request = new WindyRequest(
                    location.latitude(),
                    location.longitude(),
                    "iconEu",
                    List.of("temp", "rh", "wind", "precip", "convPrecip", "wind"),
                    List.of("surface", "950h", "900h", "850h", "800h", "700h"),
                    windyApiKey
            );

            WindyResponse windyResponse = windyClient.getPointForecast(request);

            return formatMessage(location, windyResponse);
        } catch (Exception e) {
            log.error("Ошибка при получении данных от Windy", e);
            return "❌ Ошибка получения данных от Windy";
        }
    }

    private String formatMessage(
            GeocodingResponse.Location location,
            WindyResponse response
    ) {

        List<ForecastSlice> slices = ForecastOffset.FIXED.stream()
                .map(o -> ForecastSlice.from(response, o))
                .toList();

        return MessageView.render(location, slices);
    }
}