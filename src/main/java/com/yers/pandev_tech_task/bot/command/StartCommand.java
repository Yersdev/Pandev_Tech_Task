package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.service.CategoryService;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Команда "/start" для приветствия пользователя и отображения списка доступных команд.
 */
@Component
@RequiredArgsConstructor
public class StartCommand implements CommandProcessor {
    /**
     * Проверяет, поддерживается ли переданная команда.
     *
     * @param command строка команды
     * @return {@code true}, если команда начинается с "/start", иначе {@code false}
     */
    @Override
    public boolean supports(String command) {
        return command.startsWith("/start");
    }

    /**
     * Обрабатывает команду "/start", отправляя приветственное сообщение и список команд.
     *
     * @param chatId  идентификатор чата пользователя
     * @param command строка команды (не используется в обработке)
     * @return приветственное сообщение со списком доступных команд
     */
    @Override
    public String process(Long chatId, String command) {
        return TextsHelperUtil.startText();
    }
}
