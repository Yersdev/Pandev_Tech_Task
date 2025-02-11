package com.yers.pandev_tech_task.service;

import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.model.User;
import com.yers.pandev_tech_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    @Value("${security.admin-secret}")
    private String adminSecret;

    public Role getUserRole(Long chatId) {
        return userRepository.findById(chatId)
                .map(User::getRole)
                .orElse(Role.User);
    }

public String downgradeRole(Long chatId) {
        User user = userRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() == Role.User) {
        return "ℹ️ У вас уже минимальная роль (User).";
        }
        user.setRole(Role.User);
        userRepository.save(user);
        return "✅ Вы стали пользователем!";
}


    public String upgradeToAdmin(Long chatId, String secret) {
        if (!secret.equals(adminSecret)) {
            return "❌ Неверное секретное слово!";
        }

        User user = userRepository.findById(chatId)
                .orElse(new User(chatId, "Unknown", Role.User));

        if (user.getRole() == Role.Admin) {
            return "✅ Вы уже админ!";
        }

        user.setRole(Role.Admin);
        userRepository.save(user);

        return String.format("""
        🎉 ✅ Вы стали админом! 
        
        %s
        """, getHelpMessageAdmin());
    }


    private String getHelpMessageAdmin() {
        return """
        📌 **Доступные команды для Админа:**
        
        🔹 `/viewTree` - Просмотр дерева категорий.
        🔹 `/addElement <название>` - Добавить корневой элемент.
        🔹 `/addElement <родитель> <дочерний>` - Добавить подкатегорию.
        🔹 `/removeElement <название>` - Удалить элемент (и все его подкатегории).
        🔹 `/download` - Скачать Excel-файл с категориями.
        🔹 `/upload` - Загрузить Excel-файл с категориями.
        🔹 `/help` - Показать список доступных команд.
        
        ✨ **Бот для управления деревом категорий.**""";
    }
}
