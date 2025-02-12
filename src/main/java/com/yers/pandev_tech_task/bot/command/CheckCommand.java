package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.service.AuthService;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Команда для проверки и повышения пользователя до администратора.
 */
@Component
@RequiredArgsConstructor
public class CheckCommand implements CommandProcessor {
    private final AuthService authService;

    /**
     * Проверяет, поддерживается ли переданная команда.
     *
     * @param command строка команды
     * @return {@code true}, если команда начинается с "/check", иначе {@code false}
     */
    @Override
    public boolean supports(String command) {
        return command.startsWith("/check");
    }

    /**
     * Обрабатывает команду проверки и обновления роли пользователя.
     *
     * @param chatId  идентификатор чата пользователя
     * @param command строка команды в формате "/check <секретное_слово>"
     * @return сообщение с результатом выполнения команды
     */
    @Override
    public String process(Long chatId, String command) {
        String[] parts = command.split(" ", 2);

        if (parts.length < 2) {
            return TextsHelperUtil.warnHowToUsePassword();
        }

        return authService.upgradeToAdmin(chatId, parts[1]);
    }
}
