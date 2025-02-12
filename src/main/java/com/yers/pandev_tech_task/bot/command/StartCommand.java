package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Команда "/start" для приветствия пользователя и отображения списка доступных команд.
 */
@Component
@RequiredArgsConstructor
public class StartCommand implements CommandProcessor {
    private final CategoryService categoryService;

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
        return """
            👋 Приветствуем вас в Телеграм-боте 'Дерево Категорий'!
            
            Этот бот демонстрирует мои навыки и возможности работы с категориями.
            Доступные команды:
            ✅ /help — список команд
            ✅ /viewTree — показать дерево категорий
            ✅ /check <секретное_слово> — проверить доступ к роли админа
            ✅ /removeElement <категория> — удалить категорию (⚠️ только для админов)
            ✅ /addElement <родительская_категория> <дочерняя_категория> — добавить категорию (⚠️ только для админов)
            ✅ /download — скачать Excel-файл с категориями
            ✅ /upload — загрузить Excel-файл с категориями
            ✅ /downgrade — понизить роль пользователя (⚠️ только для админов)
            """;
    }
}
