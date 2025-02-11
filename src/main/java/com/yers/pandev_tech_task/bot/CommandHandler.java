package com.yers.pandev_tech_task.bot;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandHandler {
    private final List<CommandProcessor> processors;

    public String handleCommand(Long chatId, String command) {
        return processors.stream()
                .filter(p -> p.supports(command))
                .findFirst()
                .map(p -> p.process(chatId, command))
                .orElse("Неизвестная команда. Используйте /help.");
    }
}
