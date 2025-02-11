package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements CommandProcessor {
    private final AuthService authService;

    @Override
    public boolean supports(String command) {
        return "/help".equals(command);
    }

    @Override
    public String process(Long chatId, String command) {
        if (isAdmin(chatId)) {
            return """
            📌 Доступные команды для админа:
            ✅ /help — список команд
            ✅ /viewTree — показать дерево категорий
            ✅ /check <секретное_слово> — проверить доступ для доступа к роли админа
            ✅ /removeElement <категория> — удалить категорию (только для админом)
            ✅ /addElement <родительская_категория> <дочерняя_категория> — добавить категорию
            ✅ /download — скачать Excel-файл с категориями
            ✅ /upload — загрузить Excel-файл с категориями
            ✅ /downgrade — Понизить роль пользователя
            """;
        }
        else {
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
    private boolean isAdmin(Long chatId) {
        return authService.getUserRole(chatId) == Role.Admin;
    }
}
