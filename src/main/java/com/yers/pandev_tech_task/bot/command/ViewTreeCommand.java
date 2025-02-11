package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViewTreeCommand implements CommandProcessor {
    private final CategoryService categoryService ;

    @Override
    public boolean supports(String command) {
        return "/viewTree".equals(command);
    }

    @Override
    public String process(Long chatId, String command) {
        return categoryService.getCategoryTree();
    }
}
