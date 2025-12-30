package ru.salex.weather.bot.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.salex.weather.bot.commands.CommandHandler;

@Slf4j
@Component
@Profile("!test")
public class WindyBot implements SpringLongPollingBot,
        LongPollingSingleThreadUpdateConsumer {

    private final CommandHandler commandHandler;

    @Value("${bot.token}")
    private String botToken;

    public WindyBot(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        commandHandler.handle(update);
    }

    @PostConstruct
    void logBotToken() {
        log.info("BOT_TOKEN present = {}",
                botToken != null && !botToken.isBlank());
    }
}