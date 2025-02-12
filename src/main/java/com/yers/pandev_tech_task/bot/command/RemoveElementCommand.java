package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.BaseCommandProcessor;
import com.yers.pandev_tech_task.service.AuthService;
import com.yers.pandev_tech_task.service.CategoryService;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import org.springframework.stereotype.Component;

/**
 * Команда для удаления категории.
 * Доступна только администраторам.
 */
@Component
public class RemoveElementCommand extends BaseCommandProcessor {
    private final CategoryService categoryService;

    /**
     * Конструктор для создания команды удаления категории.
     *
     * @param authService     сервис аутентификации
     * @param categoryService сервис управления категориями
     */
    public RemoveElementCommand(AuthService authService, CategoryService categoryService) {
        super(authService);
        this.categoryService = categoryService;
    }

    /**
     * Проверяет, поддерживается ли переданная команда.
     *
     * @param command строка команды
     * @return {@code true}, если команда начинается с "/removeElement", иначе {@code false}
     */
    @Override
    public boolean supports(String command) {
        return command.startsWith("/removeElement");
    }

    /**
     * Обрабатывает команду удаления категории.
     * Проверяет права администратора перед выполнением.
     *
     * @param chatId  идентификатор чата пользователя
     * @param command строка команды в формате "/removeElement <категория>"
     * @return сообщение с результатом выполнения команды
     */
    @Override
    public String process(Long chatId, String command) {
        if (!isAdmin(chatId)) {
            return TextsHelperUtil.noEnoughRightToDelete();
        }

        String[] parts = command.split(" ", 2);
        if (parts.length != 2 || parts[1].trim().isEmpty()) {
            return TextsHelperUtil.warnHowToRemoveElementFromCategory();
        }

        String result = categoryService.removeCategory(parts[1].trim());
        return (result == null || result.isEmpty()) ? TextsHelperUtil.nullAnswerFromServer() : result;
    }
}
