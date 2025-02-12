package com.yers.pandev_tech_task.bot;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Обработчик команд, который находит подходящий {@link CommandProcessor} и передает ему команду для выполнения.
 */
@Component
@RequiredArgsConstructor
public class CommandHandler {
    private final List<CommandProcessor> processors;

    /**
     * Обрабатывает входящую команду, передавая её соответствующему обработчику.
     *
     * @param chatId  идентификатор чата пользователя
     * @param command строка команды
     * @return ответ от соответствующего обработчика команды, либо сообщение об ошибке, если команда не найдена
     */
    public String handleCommand(Long chatId, String command) {
        return processors.stream()
                .filter(p -> p.supports(command))
                .findFirst()
                .map(p -> p.process(chatId, command))
                .orElse(TextsHelperUtil.wrongCommand());
    }
}
