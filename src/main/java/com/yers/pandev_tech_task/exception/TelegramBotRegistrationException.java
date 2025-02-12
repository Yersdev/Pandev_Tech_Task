package com.yers.pandev_tech_task.exception;

/**
 * Исключение, возникающее при ошибке регистрации Telegram-бота.
 * <p>
 * Это unchecked-исключение, расширяющее {@link RuntimeException}.
 * Оно может использоваться для обозначения проблем, связанных с
 * процессом регистрации бота в системе.
 * </p>
 */
public class TelegramBotRegistrationException extends RuntimeException {

    /**
     * Создает новый экземпляр исключения с указанным сообщением.
     *
     * @param message сообщение, описывающее причину ошибки
     */
    public TelegramBotRegistrationException(String message) {
        super(message);
    }
}
