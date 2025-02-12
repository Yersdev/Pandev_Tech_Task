package com.yers.pandev_tech_task.exception;

/**
 * Исключение, возникающее при ошибке отправки сообщения.
 * <p>
 * Это unchecked-исключение, расширяющее {@link RuntimeException}.
 * Оно может использоваться для обозначения проблем, связанных с
 * процессом отправки сообщений в системе.
 * </p>
 */
public class SendMessageException extends RuntimeException {

  /**
   * Создает новый экземпляр исключения с указанным сообщением.
   *
   * @param message сообщение, описывающее причину ошибки
   */
  public SendMessageException(String message) {
    super(message);
  }
}
