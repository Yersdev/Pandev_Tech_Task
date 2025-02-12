package com.yers.pandev_tech_task.repository;

import com.yers.pandev_tech_task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
