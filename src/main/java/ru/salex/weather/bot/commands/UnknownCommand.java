package ru.salex.weather.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static ru.salex.weather.bot.util.Constants.listCommand;

@Slf4j
@Component
public class UnknownCommand implements Command {
    private final TelegramClient telegramClient;

    public UnknownCommand(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Неизвестная комманда. Вот список поддерживаемых команд\n" + listCommand)
                    .build();
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException exception) {
                log.error("Ошибка при отправке сообщения", exception);
            }

        }
    }

    @Override
    public String getCommand() {
        return CommandName.UNKNOWN.getName();
    }
}