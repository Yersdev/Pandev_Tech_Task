package com.yers.pandev_tech_task.bot;

import com.yers.pandev_tech_task.bot.command.proccessor.CommandProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование CommandHandler")
class CommandHandlerTest {

    private CommandHandler commandHandler;
    private CommandProcessor mockProcessor1;
    private CommandProcessor mockProcessor2;

    @BeforeEach
    void setUp() {
        mockProcessor1 = mock(CommandProcessor.class);
        mockProcessor2 = mock(CommandProcessor.class);

        when(mockProcessor1.supports("/test1")).thenReturn(true);
        when(mockProcessor1.process(123L, "/test1")).thenReturn("Ответ от test1");

        when(mockProcessor2.supports("/test2")).thenReturn(true);
        when(mockProcessor2.process(123L, "/test2")).thenReturn("Ответ от test2");

        commandHandler = new CommandHandler(List.of(mockProcessor1, mockProcessor2));
    }

    @Test
    @DisplayName("Команда передается правильному процессору")
    void testHandleCommand_ValidCommand() {
        Long chatId = 123L;
        String response = commandHandler.handleCommand(chatId, "/test1");

        assertEquals("Ответ от test1", response);
        verify(mockProcessor1, times(1)).process(chatId, "/test1");
        verify(mockProcessor2, never()).process(anyLong(), anyString());
    }

    @Test
    @DisplayName("Неизвестная команда возвращает сообщение об ошибке")
    void testHandleCommand_UnknownCommand() {
        Long chatId = 123L;
        String response = commandHandler.handleCommand(chatId, "/unknown");

        assertEquals("❌ Неизвестная команда. Используйте /help для просмотра доступных команд.", response);
        verify(mockProcessor1, never()).process(anyLong(), anyString());
        verify(mockProcessor2, never()).process(anyLong(), anyString());
    }

    @Test
    @DisplayName("Обрабатывается первая найденная подходящая команда")
    void testHandleCommand_FirstMatchingProcessorUsed() {
        when(mockProcessor1.supports("/common")).thenReturn(true);
        when(mockProcessor2.supports("/common")).thenReturn(true);
        when(mockProcessor1.process(123L, "/common")).thenReturn("Ответ от первого процессора");
        when(mockProcessor2.process(123L, "/common")).thenReturn("Ответ от второго процессора");

        Long chatId = 123L;
        String response = commandHandler.handleCommand(chatId, "/common");

        assertEquals("Ответ от первого процессора", response);
        verify(mockProcessor1, times(1)).process(chatId, "/common");
        verify(mockProcessor2, never()).process(anyLong(), anyString());
    }
}
