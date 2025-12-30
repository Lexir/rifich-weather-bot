package ru.salex.weather.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.salex.weather.bot.service.WindyService;

@Slf4j
@Component
public class WeatherCommand implements Command {
    private static final String DEFAULT_CITY = "Москва";
    private final TelegramClient telegramClient;
    private final WindyService windyService;

    public WeatherCommand(TelegramClient telegramClient,
                          WindyService windyService) {
        this.telegramClient = telegramClient;
        this.windyService = windyService;
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                long chatId = update.getMessage().getChatId();
                String city = getCityOrDefault(update);
                String weather = windyService.getWeather(city);
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chatId)
                        .text(weather)
                        .parseMode("HTML")
                        .disableWebPagePreview(true)
                        .build();
                telegramClient.execute(message);
            } catch (TelegramApiException exception) {
                log.error("Ошибка при отправке сообщения", exception);
            } catch (ArrayIndexOutOfBoundsException exception) {
                log.error("Ошибка в аргументах", exception);
            }
        }
    }

    @Override
    public String getCommand() {
        return CommandName.WEATHER.getName();
    }

    private static String getCityOrDefault(Update update) {
        if (update == null
                || !update.hasMessage()
                || !update.getMessage().hasText()) {
            return DEFAULT_CITY;
        }

        String text = update.getMessage().getText().trim();

        if (text.isEmpty()) {
            return DEFAULT_CITY;
        }

        String[] args = text.split("\\s+", 2);

        if (args.length < 2 || args[1].isBlank()) {
            return DEFAULT_CITY;
        }

        return args[1].trim();
    }
}