package com.yers.pandev_tech_task.bot.command.proccessor;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import com.yers.pandev_tech_task.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DowngradeRoleCommand implements CommandProcessor {
    private final AuthService authService;

    @Override
    public boolean supports(String command) {
        return command.equals("/downgrade");
    }

    @Override
    public String process(Long chatId, String command) {
        return authService.downgradeRole(chatId);
    }
}
