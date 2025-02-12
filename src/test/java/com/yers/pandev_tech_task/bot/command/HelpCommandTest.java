package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование HelpCommand")
class HelpCommandTest {
    @Mock
    private AuthService authService;

    private HelpCommand helpCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        helpCommand = new HelpCommand(authService);
    }

    @Test
    @DisplayName("Поддержка команды /help")
    void testSupports_ValidCommand() {
        assertTrue(helpCommand.supports("/help"));
    }

    @Test
    @DisplayName("Не поддерживает другие команды")
    void testSupports_InvalidCommand() {
        assertFalse(helpCommand.supports("/other"));
    }

    @Test
    @DisplayName("Обрабатывает команду /help для администратора")
    void testProcess_Admin() {
        Long chatId = 123L;
        when(authService.getUserRole(chatId)).thenReturn(Role.Admin);

        String response = helpCommand.process(chatId, "/help");

        assertTrue(response.contains("Доступные команды для админа"));
        verify(authService).getUserRole(chatId);
    }

    @Test
    @DisplayName("Обрабатывает команду /help для пользователя")
    void testProcess_User() {
        Long chatId = 456L;
        when(authService.getUserRole(chatId)).thenReturn(Role.User);

        String response = helpCommand.process(chatId, "/help");

        assertTrue(response.contains("Доступные команды для пользователя"));
        verify(authService).getUserRole(chatId);
    }
}
