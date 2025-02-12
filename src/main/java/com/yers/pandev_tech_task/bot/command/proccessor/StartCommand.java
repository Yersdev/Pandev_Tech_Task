package com.yers.pandev_tech_task.bot.command.proccessor;

import com.yers.pandev_tech_task.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand implements CommandProcessor{
    private final CategoryService categoryService;
    @Override
    public boolean supports(String command) {
        return command.startsWith("/start");
    }

    @Override
    public String process(Long chatId, String command) {
        return """
            Приветствуем вас в Телеграм-бот 'Дерево Категории'
            
            Этот телеграм бот предназначен для показа моих способностей.
            Телеграм-бот 'Дерево Категории' имеет множество функции:
            ✅ /help — список команд
            ✅ /viewTree — показать дерево категорий
            ✅ /check <секретное_слово> — проверить доступ для доступа к роли админа
            ✅ /removeElement <категория> — удалить категорию (⚠️для админом)
            ✅ /addElement <родительская_категория> <дочерняя_категория> — добавить категорию (⚠️для админом)
            ✅ /download — скачать Excel-файл с категориями
            ✅ /upload — загрузить Excel-файл с категориями
            ✅ /downgrade — Понизить роль пользователя (⚠️для админом)
            """;
    }

}
