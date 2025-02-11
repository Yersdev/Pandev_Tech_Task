package com.yers.pandev_tech_task.bot.command.proccessor;

import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.service.AuthService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseCommandProcessor implements CommandProcessor{
    protected final AuthService authService;

    protected boolean isAdmin(Long chatId) {
        return authService.getUserRole(chatId) == Role.Admin;
    }
}
