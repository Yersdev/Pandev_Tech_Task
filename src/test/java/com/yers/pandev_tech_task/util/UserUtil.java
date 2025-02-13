package com.yers.pandev_tech_task.util;

import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.model.User;

public class UserUtil {
    public static User getJohnDoeUser () {
        return User.builder()
                .id(1L)
                .role(Role.User)
                .build();
    }
    public static User getJaneDoeAdmin () {
        return User.builder()
                .id(2L)
                .role(Role.Admin)
                .build();
    }
}
