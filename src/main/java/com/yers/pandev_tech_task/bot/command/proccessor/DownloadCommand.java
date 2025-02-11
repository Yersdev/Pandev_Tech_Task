package com.yers.pandev_tech_task.bot.command.proccessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DownloadCommand implements CommandProcessor {
    @Override
    public boolean supports(String command) {
        return false;
    }

    @Override
    public String process(Long chatId, String command) {
        return "";
    }
}
