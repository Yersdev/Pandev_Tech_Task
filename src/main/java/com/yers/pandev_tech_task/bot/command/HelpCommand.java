package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Команда для отображения списка доступных команд.
 */
@Component
@RequiredArgsConstructor
public class HelpCommand implements CommandProcessor {
    private final AuthService authService;

    /**
     * Проверяет, поддерживается ли переданная команда.
     *
     * @param command строка команды
     * @return {@code true}, если команда равна "/help", иначе {@code false}
     */
    @Override
    public boolean supports(String command) {
        return "/help".equals(command);
    }

    /**
     * Обрабатывает команду "/help", возвращая список доступных команд.
     * Список команд зависит от роли пользователя (админ или обычный пользователь).
     *
     * @param chatId  идентификатор чата пользователя
     * @param command строка команды (не используется в обработке)
     * @return строка с перечнем доступных команд
     */
    @Override
    public String process(Long chatId, String command) {
        if (isAdmin(chatId)) {
            return """
            📌 Доступные команды для админа:
            ✅ /help — список команд
            ✅ /viewTree — показать дерево категорий
            ✅ /check <секретное_слово> — проверить доступ для роли админа
            ✅ /removeElement <категория> — удалить категорию (только для админов)
            ✅ /addElement <родительская_категория> <дочерняя_категория> — добавить категорию
            ✅ /download — скачать Excel-файл с категориями
            ✅ /upload — загрузить Excel-файл с категориями
            ✅ /downgrade — понизить роль пользователя
            """;
        } else {
            return """
            📌 Доступные команды для пользователя:
            ✅ /help — список команд
            ✅ /viewTree — показать дерево категорий
            ✅ /check <секретное_слово> — проверить доступ
            ✅ /download — скачать Excel-файл с категориями
            ✅ /upload — загрузить Excel-файл с категориями
            """;
        }
    }

    /**
     * Проверяет, является ли пользователь администратором.
     *
     * @param chatId идентификатор чата пользователя
     * @return {@code true}, если пользователь имеет роль администратора, иначе {@code false}
     */
    private boolean isAdmin(Long chatId) {
        return authService.getUserRole(chatId) == Role.Admin;
    }
}
