package com.yers.pandev_tech_task.repository;

import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.model.User;
import com.yers.pandev_tech_task.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Сохранение пользователя")
    void testSaveUser() {
        // given
        User user = User.builder().id(12332131L).role(Role.User).build();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser).isNotNull();
    }

    @Test
    @DisplayName("Поиск пользователя по ID")
    void testFindUserById() {
        // given
        User user = UserUtil.getJohnDoeUser();
        User savedUser = userRepository.save(user);

        // when
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        // then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.getRole()).isEqualTo(Role.User);
    }

    @Test
    @DisplayName("Проверка роли пользователя")
    void testUserRole() {
        // given
        User user = UserUtil.getJohnDoeUser();
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getRole()).isEqualTo(Role.User);
    }

    @Test
    @DisplayName("Проверка роли администратора")
    void testAdminRole() {
        // given
        User admin = UserUtil.getJaneDoeAdmin();
        User savedAdmin = userRepository.save(admin);

        // then
        assertThat(savedAdmin.getRole()).isEqualTo(Role.Admin);
    }
}
