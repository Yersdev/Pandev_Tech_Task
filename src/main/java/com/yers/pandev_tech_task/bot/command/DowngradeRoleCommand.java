package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Команда для понижения роли пользователя.
 */
@Component
@RequiredArgsConstructor
public class DowngradeRoleCommand implements CommandProcessor {
    private final AuthService authService;

    /**
     * Проверяет, поддерживается ли переданная команда.
     *
     * @param command строка команды
     * @return {@code true}, если команда равна "/downgrade", иначе {@code false}
     */
    @Override
    public boolean supports(String command) {
        return command.equals("/downgrade");
    }

    /**
     * Обрабатывает команду понижения роли пользователя.
     *
     * @param chatId  идентификатор чата пользователя
     * @param command строка команды (не используется в обработке)
     * @return сообщение с результатом выполнения команды
     */
    @Override
    public String process(Long chatId, String command) {
        return authService.downgradeRole(chatId);
    }
}
