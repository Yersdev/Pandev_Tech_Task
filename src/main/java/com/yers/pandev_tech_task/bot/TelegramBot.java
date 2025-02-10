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

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final CommandHandler commandHandler;
    private final ExcelService excelService;

    @Value("${telegram.bot-token}")
    private String botToken;

    @Value("${telegram.bot-username}")
    private String botUsername;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();

            if (update.getMessage().hasText()) {
                String result = commandHandler.handleCommand(chatId, update.getMessage().getText());

                if (result.equals("excel")) {
                    sendExcelFile(chatId);
                } else {
                    sendMessage(chatId, result);
                }
            } else if (update.getMessage().hasDocument()) {
                Document document = update.getMessage().getDocument();
                downloadAndProcessExcel(chatId, document.getFileId());
            }
        }
    }


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
            e.printStackTrace();
            sendMessage(chatId, "❌ Ошибка при отправке файла.");
        }
    }

    public void downloadAndProcessExcel(Long chatId, String fileId) {
        try {

            org.telegram.telegrambots.meta.api.objects.File file = execute(new GetFile(fileId));
            String fileUrl = "https://api.telegram.org/file/bot" + botToken + "/" + file.getFilePath();

            InputStream inputStream = new URL(fileUrl).openStream();

            String result = excelService.importCategoriesFromExcel(inputStream);

            sendMessage(chatId, result);
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(chatId, "❌ Ошибка при загрузке файла!");
        }
    }


    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
