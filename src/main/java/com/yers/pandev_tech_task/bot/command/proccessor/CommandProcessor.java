package com.yers.pandev_tech_task.bot.command.proccessor;

public interface CommandProcessor {
    boolean supports(String command);
    String process(Long chatId, String command);
}
