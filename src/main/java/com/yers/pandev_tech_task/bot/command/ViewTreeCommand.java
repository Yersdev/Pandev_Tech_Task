package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Команда "/viewTree" для отображения дерева категорий.
 */
@Component
@RequiredArgsConstructor
public class ViewTreeCommand implements CommandProcessor {
    private final CategoryService categoryService;

    /**
     * Проверяет, поддерживается ли переданная команда.
     *
     * @param command строка команды
     * @return {@code true}, если команда равна "/viewTree", иначе {@code false}
     */
    @Override
    public boolean supports(String command) {
        return "/viewTree".equals(command);
    }

    /**
     * Обрабатывает команду и возвращает дерево категорий.
     *
     * @param chatId  идентификатор чата пользователя
     * @param command строка команды (не используется в обработке)
     * @return строковое представление дерева категорий
     */
    @Override
    public String process(Long chatId, String command) {
        return categoryService.getCategoryTree();
    }
}
