package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование StartCommand")
class StartCommandTest {

    @Mock
    private CategoryService categoryService;

    private StartCommand startCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCommand = new StartCommand();
    }

    @Test
    @DisplayName("Поддержка команды /start")
    void testSupports_ValidCommand() {
        assertTrue(startCommand.supports("/start"));
    }

    @Test
    @DisplayName("Поддержка команды /start с параметрами")
    void testSupports_ValidCommandWithArgs() {
        assertTrue(startCommand.supports("/start some_param"));
    }

    @Test
    @DisplayName("Не поддерживает другие команды")
    void testSupports_InvalidCommand() {
        assertFalse(startCommand.supports("/otherCommand"));
    }

    @Test
    @DisplayName("Корректное приветственное сообщение")
    void testProcess_WelcomeMessage() {
        Long chatId = 123L;
        String expectedResponse = """
            👋 Приветствуем вас в Телеграм-боте 'Дерево Категорий'!
            
            Этот бот демонстрирует мои навыки и возможности работы с категориями.
            Доступные команды:
            ✅ /help — список команд
            ✅ /viewTree — показать дерево категорий
            ✅ /check <секретное_слово> — проверить доступ к роли админа
            ✅ /removeElement <категория> — удалить категорию (⚠️ только для админов)
            ✅ /addElement <родительская_категория> <дочерняя_категория> — добавить категорию (⚠️ только для админов)
            ✅ /download — скачать Excel-файл с категориями
            ✅ /upload — загрузить Excel-файл с категориями
            ✅ /downgrade — понизить роль пользователя (⚠️ только для админов)
            """;

        String response = startCommand.process(chatId, "/start");

        assertEquals(expectedResponse, response);
    }
}
