package com.yers.pandev_tech_task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_users")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.User;
    public User(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}