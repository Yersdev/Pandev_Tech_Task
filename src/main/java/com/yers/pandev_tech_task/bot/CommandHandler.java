package com.yers.pandev_tech_task.bot;

import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.service.AuthService;
import com.yers.pandev_tech_task.service.CategoryService;
import com.yers.pandev_tech_task.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Document;

@Component
@RequiredArgsConstructor
public class CommandHandler {
    private final CategoryService categoryService;
    private final AuthService authService;

    public String handleCommand(Long chatId, String command) {
        //TODO: Посмотри как нужно добавить норм /start. Check можно ли видео добавить
        if (command.equals("/help")) {
            return getHelpMessage();
        }
        if (command.equals("/viewTree")) {
            return categoryService.getCategoryTree();
        }
        //TODO: Не забудь сказать или указать что этот метод не будет показываться в help, но будет использоваться для входа как админ пароль:1234
        if (command.startsWith("/check")) {
            String[] parts = command.split(" ", 2);
            if (parts.length < 2) {
                return "⚠️ Использование: /check <секретное_слово>";
            }
            return authService.upgradeToAdmin(chatId, parts[1]);
        }

        //TODO: Проверь для всех моментов, попроуй убрать середины
        Role role = authService.getUserRole(chatId);
        if (command.startsWith("/removeElement")) {
            if (role != Role.Admin) {
                return "❌ Извините но у вас к сожалению нет прав для создание/изменение/удаление данных ❌";
            }
            String[] parts = command.split(" ", 2);
            if (parts.length != 2) {
                return "⚠️ Используйте: /removeElement <категория>";
            }
            return categoryService.removeCategory(parts[1]);
        }

        if (command.startsWith("/addElement")) {
            if (role != Role.Admin) {
                return "❌ Извините но у вас к сожалению нет прав для создание/изменение/удаление данных ❌";
            }

            String[] parts = command.split(" ", 3);
            if (parts.length == 2) {
                return categoryService.addCategory(parts[1], null);
            } else if (parts.length == 3) {
                return categoryService.addCategory(parts[2], parts[1]);
            } else {
                return "⚠️ Используйте: /addElement <родительская_категория> <дочерняя_категория> ⚠️";
            }
        }

        //TODO: Лагает нужно найти другой метод, метод с habr check
        if (command.equals("/download")) {
                return "excel";
        }

        if (command.equals("/upload")) {
            return "📂 Отправьте Excel-файл с категориями в чат.";
        }
        return "Неизвестная команда. Используйте /help.";
    }
    private String getHelpMessage() {
        return """
        📌 **Доступные команды для простых Юзеров:**
        
        🔹 `/viewTree` - Просмотр дерева категорий.
        🔹 `/download` - Скачать Excel-файл с категориями.
        🔹 `/upload` - Загрузить Excel-файл с категориями.
        🔹 `/help` - Показать список доступных команд.
        
        ✨ **Бот для управления деревом категорий.**""";
    }
}
