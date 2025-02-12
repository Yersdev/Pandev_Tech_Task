//package com.yers.pandev_tech_task.service;
//
//import com.yers.pandev_tech_task.bot.TelegramBot;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
//import org.telegram.telegrambots.meta.api.objects.InputFile;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//@Service
//@RequiredArgsConstructor
//public class DownloadService {
//    private final ExcelService excelService;
//
//    public SendDocument sendExcelFile(Long chatId) {
//        try {
//            File excelData = excelService.exportCategoriesToExcel();
//
//            // Создаем временный файл
//            File tempFile = File.createTempFile("categories_", ".xlsx");
//            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
//                fos.write(excelData);
//            }
//
//            // Отправляем файл через Telegram
//            SendDocument sendDocument = SendDocument.builder()
//                    .chatId(chatId.toString())
//                    .document(new InputFile(tempFile, "categories.xlsx"))
//                    .caption("📂 Вот ваш список категорий в Excel")
//                    .build();
//
//            return sendDocument;
//         } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
