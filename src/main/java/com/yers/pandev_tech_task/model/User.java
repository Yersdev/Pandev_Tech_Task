package com.yers.pandev_tech_task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность пользователя в системе.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_users")
public class User {

    /** Уникальный идентификатор пользователя. */
    @Id
    private Long id;

    /** Имя пользователя (логин). */
    private String username;

    /** Роль пользователя в системе (по умолчанию {@link Role#User}). */
    @Enumerated(EnumType.STRING)
    private Role role = Role.User;

    /**
     * Конструктор для создания пользователя без пароля.
     *
     * @param id       идентификатор пользователя
     * @param username имя пользователя
     * @param role     роль пользователя
     */
}
