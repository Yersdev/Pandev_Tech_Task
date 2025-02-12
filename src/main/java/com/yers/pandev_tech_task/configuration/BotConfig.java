package com.yers.pandev_tech_task.configuration;

import com.yers.pandev_tech_task.bot.TelegramBot;
import com.yers.pandev_tech_task.exception.TelegramBotRegistrationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Конфигурация для регистрации Telegram-бота в Spring-контексте.
 */
@Configuration
public class BotConfig {

    /**
     * Создаёт и регистрирует экземпляр {@link TelegramBotsApi}, который управляет ботами.
     *
     * @param telegramBot Экземпляр Telegram-бота.
     * @return TelegramBotsApi с зарегистрированным ботом.
     */
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
            return botsApi;
        } catch (TelegramApiException e) {
            //TODO: Показать Даурену
            throw new TelegramBotRegistrationException("Ошибка при регистрации Telegram-бота");
        }
    }
}