package com.yers.pandev_tech_task.bot.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование UploadCommand")
class UploadCommandTest {

    private UploadCommand uploadCommand;

    @BeforeEach
    void setUp() {
        uploadCommand = new UploadCommand();
    }

    @Test
    @DisplayName("Поддержка команды /upload")
    void testSupports_ValidCommand() {
        assertTrue(uploadCommand.supports("/upload"));
    }

    @Test
    @DisplayName("Не поддерживает другие команды")
    void testSupports_InvalidCommand() {
        assertFalse(uploadCommand.supports("/start"));
        assertFalse(uploadCommand.supports("/help"));
        assertFalse(uploadCommand.supports("/download"));
    }

    @Test
    @DisplayName("Корректный ответ при обработке команды /upload")
    void testProcess_UploadResponse() {
        Long chatId = 123L;
        String response = uploadCommand.process(chatId, "/upload");
        assertEquals("upload", response);
    }
}
