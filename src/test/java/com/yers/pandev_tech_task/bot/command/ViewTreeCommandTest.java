package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование ViewTreeCommand")
class ViewTreeCommandTest {

    private ViewTreeCommand viewTreeCommand;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = mock(CategoryService.class);
        viewTreeCommand = new ViewTreeCommand(categoryService);
    }

    @Test
    @DisplayName("Поддержка команды /viewTree")
    void testSupports_ValidCommand() {
        assertTrue(viewTreeCommand.supports("/viewTree"));
    }

    @Test
    @DisplayName("Не поддерживает другие команды")
    void testSupports_InvalidCommand() {
        assertFalse(viewTreeCommand.supports("/help"));
        assertFalse(viewTreeCommand.supports("/download"));
        assertFalse(viewTreeCommand.supports("/upload"));
    }

    @Test
    @DisplayName("Возвращает дерево категорий")
    void testProcess_ReturnsCategoryTree() {
        Long chatId = 123L;
        String expectedTree = "📂 Категории:\n - Фильмы\n - Книги\n - Музыка";

        when(categoryService.getCategoryTree()).thenReturn(expectedTree);

        String response = viewTreeCommand.process(chatId, "/viewTree");

        assertEquals(expectedTree, response);
        verify(categoryService, times(1)).getCategoryTree();
    }
}
