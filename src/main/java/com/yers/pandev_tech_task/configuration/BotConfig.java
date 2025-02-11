package com.yers.pandev_tech_task.configuration;

import com.yers.pandev_tech_task.bot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBot);
        return botsApi;
    }
    private String getHelpMessage() {
        return """
        📌 **Доступные команды для простых Юзеров:**
        
        1️⃣ `/viewTree` - Просмотр дерева категорий.
        2️⃣ `/download` - Скачать Excel-файл с категориями.
        3️⃣ `/upload` - Загрузить Excel-файл с категориями.
        4️⃣ `/help` - Показать список доступных команд.
        
        ✨ **Бот для управления деревом категорий.**""";
    }
}
