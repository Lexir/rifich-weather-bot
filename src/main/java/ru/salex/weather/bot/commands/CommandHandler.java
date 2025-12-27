package ru.salex.weather.bot.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.salex.weather.bot.commands.CommandName.UNKNOWN;

@Service
public class CommandHandler {
    private final Map<String, Command> commands;

    public CommandHandler(Collection<Command> commands) {
        this.commands = commands
                .stream()
                .collect(Collectors.toMap(Command::getCommand, Function.identity()));
    }

    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String commandName = getCommandName(update);
            Command handleCommand = commands.get(commandName);
            if (handleCommand != null) {
                handleCommand.handle(update);
            } else {
                commands.get(UNKNOWN.getName()).handle(update);
            }
        }
    }

    private String getCommandName(Update update) {
        return update.getMessage()
                .getText()
                .substring(1)
                .split(" ")[0];
    }
}