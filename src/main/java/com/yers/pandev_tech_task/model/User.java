package com.yers.pandev_tech_task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность пользователя в системе.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "t_users")
public class User {

    /** Уникальный идентификатор пользователя. */
    @Id
    private Long id;

    /** Роль пользователя в системе (по умолчанию {@link Role#User}). */
    @Enumerated(EnumType.STRING)
    private Role role = Role.User;
}
