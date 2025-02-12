package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Команда для скачивания данных в формате Excel.
 */
@Component
@RequiredArgsConstructor
public class DownloadCommand implements CommandProcessor {

    /**
     * Проверяет, поддерживается ли переданная команда.
     *
     * @param command строка команды
     * @return {@code true}, если команда равна "/download", иначе {@code false}
     */
    @Override
    public boolean supports(String command) {
        return "/download".equals(command);
    }

    /**
     * Обрабатывает команду скачивания.
     *
     * @param chatId  идентификатор чата пользователя
     * @param command строка команды (не используется в обработке)
     * @return строка, указывающая на формат скачиваемых данных (в данном случае "excel")
     */
    @Override
    public String process(Long chatId, String command) {
        return "excel";
    }
}
