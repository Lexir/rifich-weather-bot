package ru.salex.weather.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
public class AboutCommand implements Command {
    private final TelegramClient telegramClient;

    public AboutCommand(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    @Override
    public void handle(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Я - Telegram бот, который может помочь вам найти погоду")
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
        return CommandName.ABOUT.getName();
    }
}