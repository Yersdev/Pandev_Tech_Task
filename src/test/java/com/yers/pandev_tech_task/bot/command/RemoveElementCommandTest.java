package com.yers.pandev_tech_task.bot.command;

import com.yers.pandev_tech_task.service.AuthService;
import com.yers.pandev_tech_task.service.CategoryService;
import com.yers.pandev_tech_task.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование RemoveElementCommand")
class RemoveElementCommandTest {
    @Mock
    private AuthService authService;

    @Mock
    private CategoryService categoryService;

    private RemoveElementCommand removeElementCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        removeElementCommand = new RemoveElementCommand(authService, categoryService);
    }

    @Test
    @DisplayName("Поддержка команды /removeElement")
    void testSupports_ValidCommand() {
        assertTrue(removeElementCommand.supports("/removeElement category"));
    }

    @Test
    @DisplayName("Не поддерживает другие команды")
    void testSupports_InvalidCommand() {
        assertFalse(removeElementCommand.supports("/otherCommand"));
    }

    @Test
    @DisplayName("Отказывает в удалении, если пользователь не админ")
    void testProcess_NotAdmin() {
        Long chatId = 123L;
        when(authService.getUserRole(chatId)).thenReturn(Role.User);

        String response = removeElementCommand.process(chatId, "/removeElement category");

        assertEquals("❌ У вас нет прав для удаления данных ❌", response);
        verify(authService).getUserRole(chatId);
        verifyNoInteractions(categoryService);
    }

    @Test
    @DisplayName("Возвращает ошибку при неверном формате команды")
    void testProcess_InvalidFormat() {
        Long chatId = 123L;
        when(authService.getUserRole(chatId)).thenReturn(Role.Admin);

        String response = removeElementCommand.process(chatId, "/removeElement");

        assertEquals("⚠️ Используйте: /removeElement <категория>", response);
        verify(authService).getUserRole(chatId);
        verifyNoInteractions(categoryService);
    }

    @Test
    @DisplayName("Успешное удаление категории")
    void testProcess_Success() {
        Long chatId = 123L;
        String category = "Books";
        when(authService.getUserRole(chatId)).thenReturn(Role.Admin);
        when(categoryService.removeCategory(category)).thenReturn("✅ Категория удалена: Books");

        String response = removeElementCommand.process(chatId, "/removeElement " + category);

        assertEquals("✅ Категория удалена: Books", response);
        verify(authService).getUserRole(chatId);
        verify(categoryService).removeCategory(category);
    }

    @Test
    @DisplayName("Обрабатывает пустой ответ от CategoryService")
    void testProcess_EmptyResponse() {
        Long chatId = 123L;
        String category = "Books";
        when(authService.getUserRole(chatId)).thenReturn(Role.Admin);
        when(categoryService.removeCategory(category)).thenReturn("");

        String response = removeElementCommand.process(chatId, "/removeElement " + category);

        assertEquals("❌ Ошибка: пустой ответ от сервиса", response);
        verify(authService).getUserRole(chatId);
        verify(categoryService).removeCategory(category);
    }
}
