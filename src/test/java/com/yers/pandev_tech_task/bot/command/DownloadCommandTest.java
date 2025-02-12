package com.yers.pandev_tech_task.bot.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование DownloadCommand")
class DownloadCommandTest {
    private DownloadCommand downloadCommand;

    @BeforeEach
    void setUp() {
        downloadCommand = new DownloadCommand();
    }

    @Test
    @DisplayName("Поддержка команды /download")
    void testSupports_ValidCommand() {
        assertTrue(downloadCommand.supports("/download"));
    }

    @Test
    @DisplayName("Не поддерживает другие команды")
    void testSupports_InvalidCommand() {
        assertFalse(downloadCommand.supports("/other"));
    }

    @Test
    @DisplayName("Обрабатывает команду /download и возвращает 'excel'")
    void testProcess_ReturnsExcel() {
        Long chatId = 123L;
        String result = downloadCommand.process(chatId, "/download");

        assertEquals("excel", result);
    }
}
