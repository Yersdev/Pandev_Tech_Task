package com.yers.pandev_tech_task.bot.command.proccessor;

/**
 * Интерфейс для обработки команд в боте.
 */
public interface CommandProcessor {

    /**
     * Проверяет, поддерживается ли данная команда процессором.
     *
     * @param command строка команды
     * @return {@code true}, если команда поддерживается, иначе {@code false}
     */
    boolean supports(String command);

    /**
     * Обрабатывает команду и возвращает ответ.
     *
     * @param chatId  идентификатор чата
     * @param command строка команды
     * @return результат обработки команды
     */
    String process(Long chatId, String command);
}
