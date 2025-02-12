package com.yers.pandev_tech_task.util;

import com.yers.pandev_tech_task.model.Role;
import com.yers.pandev_tech_task.model.User;

public class UserUtils {
    public static User getJohnDoeUser () {
        return User.builder()
                .role(Role.User)
                .build();
    }
    public static User getJaneDoeAdmin () {
        return User.builder()
                .role(Role.Admin)
                .build();
    }
}
