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
                String[] args = update.getMessage().getText().split(" ");
                String cityArgs = "Москва";
                if (args.length == 2) {
                    cityArgs = args[1];
                }
                String weather = windyService.getWeather(cityArgs);
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chatId)
                        .text(weather)
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
}