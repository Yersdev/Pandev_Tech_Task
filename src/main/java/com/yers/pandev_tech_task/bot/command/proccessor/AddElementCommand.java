package com.yers.pandev_tech_task.bot.command.proccessor;

import com.yers.pandev_tech_task.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddElementCommand implements CommandProcessor{
    private final CategoryService categoryService;
    @Override
    public boolean supports(String command) {
        return command.startsWith("/addElement");
    }

    @Override
    public String process(Long chatId, String command) {
        String[] parts = command.split(" ", 3); // Исправленный split

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
