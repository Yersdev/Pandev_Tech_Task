package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование CheckCommand")
class CheckCommandTest {
    private AuthService authService;
    private CheckCommand checkCommand;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        checkCommand = new CheckCommand(authService);
    }

    @Test
    @DisplayName("Поддержка команды /check")
    void testSupports_ValidCommand() {
        assertTrue(checkCommand.supports("/check secret"));
    }

    @Test
    @DisplayName("Не поддерживает другие команды")
    void testSupports_InvalidCommand() {
        assertFalse(checkCommand.supports("/other"));
    }

    @Test
    @DisplayName("Ошибка при отсутствии секретного слова")
    void testProcess_MissingSecretWord() {
        Long chatId = 123L;
        String result = checkCommand.process(chatId, "/check");

        assertEquals("⚠️ Использование: /check <секретное_слово>", result);
        verify(authService, never()).upgradeToAdmin(anyLong(), anyString());
    }

    @Test
    @DisplayName("Обновление роли до администратора")
    void testProcess_UpgradeToAdmin() {
        Long chatId = 123L;
        String secretWord = "mySecret";
        when(authService.upgradeToAdmin(chatId, secretWord)).thenReturn("✅ Роль обновлена!");

        String result = checkCommand.process(chatId, "/check " + secretWord);

        assertEquals("✅ Роль обновлена!", result);
        verify(authService).upgradeToAdmin(chatId, secretWord);
    }
}
