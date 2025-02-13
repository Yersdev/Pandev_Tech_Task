package com.yers.pandev_tech_task.service;

import com.yers.pandev_tech_task.exception.UserNotFoundException;
import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.model.User;
import com.yers.pandev_tech_task.repository.UserRepository;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AuthServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private final Long userChatId = 123L;
    private final Long adminChatId = 456L;
    private final String correctSecret = "adminSecret";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(authService, "adminSecret", correctSecret);
    }

    @Test
    @DisplayName("Получение роли пользователя")
    void testGetUserRole() {
        User user = new User(userChatId, Role.User);
        when(userRepository.findById(userChatId)).thenReturn(Optional.of(user));

        Role role = authService.getUserRole(userChatId);

        assertThat(role).isEqualTo(Role.User);
    }

    @Test
    @DisplayName("Получение роли для несуществующего пользователя (по умолчанию User)")
    void testGetRoleForNonExistingUser() {
        when(userRepository.findById(userChatId)).thenReturn(Optional.empty());

        Role role = authService.getUserRole(userChatId);

        assertThat(role).isEqualTo(Role.User);
    }

    @Test
    @DisplayName("Понижение роли до User")
    void testDowngradeRoleToUser() {
        User admin = new User(adminChatId, Role.Admin);
        when(userRepository.findById(adminChatId)).thenReturn(Optional.of(admin));

        String response = authService.downgradeRole(adminChatId);

        assertThat(response).isEqualTo(TextsHelperUtil.becameUser());
        verify(userRepository).save(admin);
        assertThat(admin.getRole()).isEqualTo(Role.User);
    }

    @Test
    @DisplayName("Попытка понижения роли, если уже User")
    void testDowngradeWhenAlreadyUser() {
        User user = new User(userChatId, Role.User);
        when(userRepository.findById(userChatId)).thenReturn(Optional.of(user));

        String response = authService.downgradeRole(userChatId);

        assertThat(response).isEqualTo(TextsHelperUtil.alreadyHasUserRole());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Ошибка понижения роли - пользователь не найден")
    void testDowngradeRoleUserNotFound() {
        when(userRepository.findById(userChatId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.downgradeRole(userChatId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Попытка повысить до Admin с неверным секретом")
    void testUpgradeToAdminWrongSecret() {
        User user = new User(userChatId, Role.User);
        when(userRepository.findById(userChatId)).thenReturn(Optional.of(user));

        String response = authService.upgradeToAdmin(userChatId, "wrongSecret");

        assertThat(response).isEqualTo(TextsHelperUtil.wrongPassword());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Попытка повысить до Admin, если уже Admin")
    void testUpgradeWhenAlreadyAdmin() {
        User admin = new User(adminChatId, Role.Admin);
        when(userRepository.findById(adminChatId)).thenReturn(Optional.of(admin));

        String response = authService.upgradeToAdmin(adminChatId, correctSecret);

        assertThat(response).isEqualTo(TextsHelperUtil.AlreadyAdmin());
        verify(userRepository, never()).save(any());
    }
}
