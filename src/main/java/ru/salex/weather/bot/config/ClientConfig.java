package ru.salex.weather.bot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.salex.weather.bot.client.GeocodingClient;
import ru.salex.weather.bot.client.WindyClient;

@Configuration
public class ClientConfig {

    @Bean
    public GeocodingClient geocodingClient(@Value("${geo.api.url}") String geoUrl) {
        RestClient restClient = RestClient.builder()
                .baseUrl(geoUrl)
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(GeocodingClient.class);
    }

    @Bean
    public WindyClient windyClient(@Value("${windy.api.url}") String windyUrl) {
        RestClient restClient = RestClient.builder()
                .baseUrl(windyUrl)
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(WindyClient.class);
    }
}