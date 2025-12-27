package ru.salex.weather.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
	void handle(Update update);
	
	String getCommand();
}