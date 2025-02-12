package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.service.AuthService;
import com.yers.pandev_tech_task.service.CategoryService;
import org.springframework.stereotype.Component;

/**
 * Команда для добавления новой категории в систему.
 */
@Component
public class AddElementCommand extends BaseCommandProcessor {
    private final CategoryService categoryService;

    /**
     * Создает экземпляр команды добавления элемента.
     *
     * @param authService     сервис аутентификации пользователей
     * @param categoryService сервис управления категориями
     */
    public AddElementCommand(AuthService authService, CategoryService categoryService) {
        super(authService);
        this.categoryService = categoryService;
    }

    /**
     * Проверяет, поддерживается ли переданная команда.
     *
     * @param command строка команды
     * @return {@code true}, если команда начинается с "/addElement", иначе {@code false}
     */
    @Override
    public boolean supports(String command) {
        return command.startsWith("/addElement");
    }

    /**
     * Обрабатывает команду добавления категории.
     *
     * @param chatId  идентификатор чата
     * @param command строка команды в формате "/addElement <родительская_категория> <дочерняя_категория>"
     * @return сообщение с результатом выполнения команды
     */
    @Override
    public String process(Long chatId, String command) {
        String[] parts = command.split(" ", 3); // Разделение команды на части

        if (!isAdmin(chatId)) {
            return "❌ У вас нет прав для удаления данных ❌";
        }

        if (parts.length < 2) {
            return "⚠️ Используйте: /addElement <родительская_категория> <дочерняя_категория>";
        }

        String parentCategory = null;
        String childCategory;

        if (parts.length == 3) {
            parentCategory = parts[1];
            childCategory = parts[2];
        } else {
            childCategory = parts[1];
        }

        return categoryService.addCategory(childCategory, parentCategory);
    }
}
