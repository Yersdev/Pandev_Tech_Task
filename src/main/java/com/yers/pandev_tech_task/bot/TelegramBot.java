package com.yers.pandev_tech_task.bot;

import com.yers.pandev_tech_task.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Основной класс Telegram-бота, обрабатывающий команды и файлы.
 */
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final CommandHandler commandHandler;
    private final ExcelService excelService;

    @Value("${telegram.bot-token}")
    private String botToken;

    @Value("${telegram.bot-username}")
    private String botUsername;

    /**
     * Обрабатывает входящие обновления от Telegram.
     *
     * @param update Объект обновления, содержащий сообщение или документ.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();

            if (update.getMessage().hasText()) {
                String result = commandHandler.handleCommand(chatId, update.getMessage().getText());

                if ("excel".equals(result)) {
                    sendExcelFile(chatId);
                } else if ("upload".equals(result)) {
                    Document document = update.getMessage().getDocument();
                    downloadAndProcessExcel(chatId, document.getFileId());
                } else {
                    sendMessage(chatId, result);
                }
            } else if (update.getMessage().hasDocument()) {
                Document document = update.getMessage().getDocument();
                downloadAndProcessExcel(chatId, document.getFileId());
            }
        }
    }

    /**
     * Отправляет Excel-файл с категориями пользователю.
     *
     * @param chatId Идентификатор чата пользователя.
     */
    private void sendExcelFile(Long chatId) {
        File file = excelService.exportCategoriesToExcel();
        if (file == null) {
            sendMessage(chatId, "❌ В базе данных нет категорий!");
            return;
        }

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(new InputFile(file));

        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            sendMessage(chatId, "❌ Ошибка при отправке файла.");
        }
    }

    /**
     * Загружает и обрабатывает загруженный пользователем Excel-файл.
     *
     * @param chatId Идентификатор чата пользователя.
     * @param fileId Идентификатор файла в Telegram.
     */
    public void downloadAndProcessExcel(Long chatId, String fileId) {
        try {
            org.telegram.telegrambots.meta.api.objects.File file = execute(new GetFile(fileId));
            String fileUrl = "https://api.telegram.org/file/bot" + botToken + "/" + file.getFilePath();
            InputStream inputStream = new URL(fileUrl).openStream();

            String result = excelService.importCategoriesFromExcel(inputStream);
            sendMessage(chatId, result);
        } catch (Exception e) {
            sendMessage(chatId, "❌ Ошибка при загрузке файла!");
        }
    }

    /**
     * Отправляет текстовое сообщение пользователю.
     *
     * @param chatId Идентификатор чата пользователя.
     * @param text   Текст сообщения.
     */
    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает имя бота, заданное в конфигурации.
     *
     * @return Имя бота.
     */
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    /**
     * Возвращает токен бота, заданный в конфигурации.
     *
     * @return Токен бота.
     */
    @Override
    public String getBotToken() {
        return botToken;
    }
}
