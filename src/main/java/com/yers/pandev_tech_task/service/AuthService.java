package com.yers.pandev_tech_task.service;

import com.yers.pandev_tech_task.exception.UserNotFoundException;
import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.model.User;
import com.yers.pandev_tech_task.repository.UserRepository;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Сервис для аутентификации и управления ролями пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    @Value("${security.admin-secret}")
    private String adminSecret;

    /**
     * Получает роль пользователя по его chatId.
     *
     * @param chatId ID чата пользователя.
     * @return Роль пользователя (User или Admin).
     */
    public Role getUserRole(Long chatId) {
        return userRepository.findById(chatId)
                .map(User::getRole)
                .orElse(Role.User);
    }

    /**
     * Понижает роль пользователя до User.
     *
     * @param chatId ID чата пользователя.
     * @return Сообщение о результате операции.
     */
    public String downgradeRole(Long chatId) {
        User user = userRepository.findById(chatId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getRole() == Role.User) {
            return TextsHelperUtil.alreadyHasUserRole();
        }
        user.setRole(Role.User);
        userRepository.save(user);
        return TextsHelperUtil.becameUser();
    }

    /**
     * Повышает роль пользователя до Admin при вводе корректного секретного слова.
     *
     * @param chatId  ID чата пользователя.
     * @param secret  Секретное слово для получения роли админа.
     * @return Сообщение о результате операции.
     */
    public String upgradeToAdmin(Long chatId, String secret) {
        if (!secret.equals(adminSecret)) {
            return TextsHelperUtil.wrongPassword();
        }

        User user = userRepository.findById(chatId)
                .orElse(new User(chatId, Role.User));

        if (user.getRole() == Role.Admin) {
            return TextsHelperUtil.AlreadyAdmin();
        }

        user.setRole(Role.Admin);
        userRepository.save(user);

        return String.format(TextsHelperUtil.becameAdmin(), getHelpMessageAdmin());
    }

    /**
     * Возвращает список доступных команд для администратора.
     *
     * @return Строка с командами.
     */
    private String getHelpMessageAdmin() {
        return TextsHelperUtil.adminPanel();
    }
}
