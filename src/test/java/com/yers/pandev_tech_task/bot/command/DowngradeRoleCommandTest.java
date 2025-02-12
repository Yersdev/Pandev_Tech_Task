package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование DowngradeRoleCommand")
class DowngradeRoleCommandTest {
    private AuthService authService;
    private DowngradeRoleCommand downgradeRoleCommand;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        downgradeRoleCommand = new DowngradeRoleCommand(authService);
    }

    @Test
    @DisplayName("Поддержка команды /downgrade")
    void testSupports_ValidCommand() {
        assertTrue(downgradeRoleCommand.supports("/downgrade"));
    }

    @Test
    @DisplayName("Не поддерживает другие команды")
    void testSupports_InvalidCommand() {
        assertFalse(downgradeRoleCommand.supports("/other"));
    }

    @Test
    @DisplayName("Обновление роли на пониженную")
    void testProcess_DowngradeRole() {
        Long chatId = 123L;
        when(authService.downgradeRole(chatId)).thenReturn("⚠️ Ваша роль была понижена.");

        String result = downgradeRoleCommand.process(chatId, "/downgrade");

        assertEquals("⚠️ Ваша роль была понижена.", result);
        verify(authService).downgradeRole(chatId);
    }
}
