package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.service.AuthService;
import lombok.RequiredArgsConstructor;

/**
 * Абстрактный базовый класс для процессоров команд.
 * Предоставляет методы для проверки роли пользователя.
 */
@RequiredArgsConstructor
public abstract class BaseCommandProcessor implements CommandProcessor {
    /** Сервис аутентификации пользователей. */
    protected final AuthService authService;

    /**
     * Проверяет, является ли пользователь администратором.
     *
     * @param chatId идентификатор чата пользователя
     * @return {@code true}, если пользователь имеет роль администратора, иначе {@code false}
     */
    protected boolean isAdmin(Long chatId) {
        return authService.getUserRole(chatId) == Role.Admin;
    }
}
