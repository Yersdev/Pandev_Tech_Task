package com.yers.pandev_tech_task.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Интерфейс для реализации команд (Command)
 */
@Component
public interface Command {
    void execute(Update update, AbsSender sender);
}