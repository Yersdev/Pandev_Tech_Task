package com.yers.pandev_tech_task.bot.command.proccessor;

import com.yers.pandev_tech_task.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckCommand implements CommandProcessor{
    private final AuthService authService;
    @Override
    public boolean supports(String command) {
        return command.startsWith("/check");
    }

    @Override
    public String process(Long chatId, String command) {
        String[] parts = command.split(" ", 2);
        if(parts.length < 2){
            return "⚠\uFE0F Использование: /check <секретное_слово>";
        }
        log.info(parts[1]);

        return authService.upgradeToAdmin(chatId, parts[1]);
    }
}
