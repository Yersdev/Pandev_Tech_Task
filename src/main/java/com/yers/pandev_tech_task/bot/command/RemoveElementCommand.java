package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.BaseCommandProcessor;
import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.service.AuthService;
import com.yers.pandev_tech_task.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class RemoveElementCommand extends BaseCommandProcessor {
    private final CategoryService categoryService;

    public RemoveElementCommand(AuthService authService, CategoryService categoryService) {
        super(authService);
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(String command) {
        return command.startsWith("/removeElement");
    }

    @Override
    public String process(Long chatId, String command) {
        if (!isAdmin(chatId)) {
            return "❌ У вас нет прав для удаления данных ❌";
        }

        String[] parts = command.split(" ", 2);
        if (parts.length != 2 || parts[1].trim().isEmpty()) {
            return "⚠️ Используйте: /removeElement <категория>";
        }

        String result = categoryService.removeCategory(parts[1].trim());
        return (result == null || result.isEmpty()) ? "❌ Ошибка: пустой ответ от сервиса" : result;
    }

}