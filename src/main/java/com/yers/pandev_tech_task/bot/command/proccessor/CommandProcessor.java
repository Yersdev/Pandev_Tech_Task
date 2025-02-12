package com.yers.pandev_tech_task.bot.command.proccessor;

import java.io.IOException;

public interface CommandProcessor {
    boolean supports(String command);
    String process(Long chatId, String command);
}
