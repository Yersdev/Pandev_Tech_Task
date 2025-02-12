package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.service.AuthService;
import com.yers.pandev_tech_task.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование AddElementCommand")
class AddElementCommandTest {

    private AuthService authService;
    private CategoryService categoryService;
    private AddElementCommand addElementCommand;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        categoryService = mock(CategoryService.class);
        addElementCommand = new AddElementCommand(authService, categoryService);
    }

    @Test
    @DisplayName("Поддержка валидных команд")
    void testSupports_ValidCommand() {
        assertTrue(addElementCommand.supports("/addElement"));
        assertTrue(addElementCommand.supports("/addElement Category1 Category2"));
    }

    @Test
    @DisplayName("Поддержка невалидных команд")
    void testSupports_InvalidCommand() {
        assertFalse(addElementCommand.supports("/deleteElement"));
        assertFalse(addElementCommand.supports("/add"));
    }

    @Test
    @DisplayName("Пользователь не является админом")
    void testProcess_UserIsNotAdmin() {
        Long chatId = 123L;
        when(authService.getUserRole(chatId)).thenReturn(Role.User); // Правильное мокирование

        String result = addElementCommand.process(chatId, "/addElement Category1 Category2");

        assertEquals("❌ У вас нет прав для удаления данных ❌", result);
        verify(categoryService, never()).addCategory(any(), any());
    }

    @Test
    @DisplayName("Недостаточно аргументов")
    void testProcess_MissingArguments() {
        Long chatId = 123L;
        when(authService.getUserRole(chatId)).thenReturn(Role.Admin); // Правильное мокирование

        String result = addElementCommand.process(chatId, "/addElement");

        assertEquals("⚠️ Используйте: /addElement <родительская_категория> <дочерняя_категория>", result);
        verify(categoryService, never()).addCategory(any(), any());
    }

    @Test
    @DisplayName("Добавление категории с родителем")
    void testProcess_AddCategory_WithParent() {
        Long chatId = 123L;
        when(authService.getUserRole(chatId)).thenReturn(Role.Admin);
        when(categoryService.addCategory("Category2", "Category1"))
                .thenReturn("✅ Добавлен дочерний элемент 'Category2' в 'Category1'");

        String result = addElementCommand.process(chatId, "/addElement Category1 Category2");

        assertEquals("✅ Добавлен дочерний элемент 'Category2' в 'Category1'", result);
        verify(categoryService).addCategory("Category2", "Category1");
    }

    @Test
    @DisplayName("Добавление категории без родителя")
    void testProcess_AddCategory_WithoutParent() {
        Long chatId = 123L;
        when(authService.getUserRole(chatId)).thenReturn(Role.Admin);
        when(categoryService.addCategory("Category1", null))
                .thenReturn("✅ Добавлена корневая категория: Category1");

        String result = addElementCommand.process(chatId, "/addElement Category1");

        assertEquals("✅ Добавлена корневая категория: Category1", result);
        verify(categoryService).addCategory("Category1", null);
    }
}
