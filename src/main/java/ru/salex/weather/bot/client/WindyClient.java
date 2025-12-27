package ru.salex.weather.bot.client;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import ru.salex.weather.bot.dto.WindyRequest;
import ru.salex.weather.bot.dto.WindyResponse;

public interface WindyClient {
    @PostExchange
    WindyResponse getPointForecast(@RequestBody WindyRequest request);
}