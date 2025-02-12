package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import org.springframework.stereotype.Component;

/**
 * Команда "/upload" для загрузки Excel-файла с категориями.
 */
@Component
public class UploadCommand implements CommandProcessor {

    /**
     * Проверяет, поддерживается ли переданная команда.
     *
     * @param command строка команды
     * @return {@code true}, если команда равна "/upload", иначе {@code false}
     */
    @Override
    public boolean supports(String command) {
        return "/upload".equals(command);
    }

    /**
     * Обрабатывает команду загрузки файла.
     *
     * @param chatId  идентификатор чата пользователя
     * @param command строка команды (не используется в обработке)
     * @return строка "upload" (заглушка для будущей логики загрузки)
     */
    @Override
    public String process(Long chatId, String command) {
        return "upload";
    }
}
