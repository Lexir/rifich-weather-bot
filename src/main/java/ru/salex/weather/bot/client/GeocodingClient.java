package ru.salex.weather.bot.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import ru.salex.weather.bot.dto.GeocodingResponse;

public interface GeocodingClient {

    @GetExchange("/search")
    GeocodingResponse searchCity(
            @RequestParam("name") String name,
            @RequestParam("count") int count,
            @RequestParam("language") String language,
            @RequestParam("format") String format
    );
}